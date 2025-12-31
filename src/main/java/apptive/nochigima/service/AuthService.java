package apptive.nochigima.service;

import static apptive.nochigima.domain.AuthProvider.GOOGLE;
import static apptive.nochigima.domain.AuthProvider.KAKAO;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import apptive.nochigima.client.GoogleAuthClient;
import apptive.nochigima.client.KakaoAuthClient;
import apptive.nochigima.config.properties.GoogleAuthProperties;
import apptive.nochigima.config.properties.KakaoAuthProperties;
import apptive.nochigima.domain.AuthProvider;
import apptive.nochigima.domain.User;
import apptive.nochigima.dto.response.AuthResponse;
import apptive.nochigima.dto.response.AuthUriResponse;
import apptive.nochigima.dto.response.GoogleAuthTokenResponse;
import apptive.nochigima.dto.response.GoogleUserInfoResponse;
import apptive.nochigima.dto.response.KakaoAuthTokenResponse;
import apptive.nochigima.dto.response.KakaoUserInfoResponse;
import apptive.nochigima.exception.UnauthorizedException;
import apptive.nochigima.repository.UserRepository;
import apptive.nochigima.util.JwtUtil;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final KakaoAuthProperties kakaoOAuthProperties;
    private final GoogleAuthProperties googleOAuthProperties;
    private final KakaoAuthClient kakaoAuthClient;
    private final GoogleAuthClient googleAuthClient;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public AuthUriResponse getKakaoAuthUri() {
        return new AuthUriResponse(kakaoOAuthProperties.authUri()
                + "?client_id="
                + kakaoOAuthProperties.clientId()
                + "&redirect_uri="
                + kakaoOAuthProperties.redirectUri()
                + "&response_type=code");
    }

    public AuthUriResponse getGoogleAuthUri() {
        return new AuthUriResponse(googleOAuthProperties.authUri()
                + "?client_id="
                + googleOAuthProperties.clientId()
                + "&redirect_uri="
                + googleOAuthProperties.redirectUri()
                + "&scope=openid&response_type=code");
    }

    @Transactional
    public AuthResponse kakaoLogin(String code) {
        KakaoAuthTokenResponse token = kakaoAuthClient.getKakaoOAuthToken(code);
        KakaoUserInfoResponse userInfo = kakaoAuthClient.getKakaoUserInfo(token);

        return getAuthResponse(userInfo.oauthId(), KAKAO);
    }

    @Transactional
    public AuthResponse kakaoLoginWithAccessToken(String kakaoAccessToken) {
        KakaoUserInfoResponse userInfo = kakaoAuthClient.getKakaoUserInfoByAccessToken(kakaoAccessToken);
        return getAuthResponse(userInfo.oauthId(), KAKAO);
    }

    @Transactional
    public AuthResponse googleLogin(String code) {
        GoogleAuthTokenResponse token = googleAuthClient.getGoogleOAuthToken(code);
        GoogleUserInfoResponse userInfo = googleAuthClient.getGoogleUserInfo(token);

        return getAuthResponse(userInfo.oauthId(), GOOGLE);
    }

    private AuthResponse getAuthResponse(String oauthId, AuthProvider authProvider) {
        User user = userRepository
                .findByOauthIdAndAuthProvider(oauthId, authProvider)
                .orElseGet(() -> userRepository.save(new User(oauthId, authProvider)));

        return issueTokens(user);
    }

    @Transactional
    public AuthResponse reissueTokens(String refreshTokenWithPrefix) {
        String refreshToken = jwtUtil.removePrefix(refreshTokenWithPrefix);
        jwtUtil.validateToken(refreshToken);

        User user = findByIdOrThrow(jwtUtil.getUserId(refreshToken));
        validateRefreshTokenWithUser(refreshToken, user);

        return issueTokens(user);
    }

    private AuthResponse issueTokens(User user) {
        String refreshToken = jwtUtil.createRefreshToken(user.getId());
        user.setRefreshToken(refreshToken);

        return new AuthResponse(jwtUtil.createAccessToken(user.getId()), refreshToken);
    }

    private User findByIdOrThrow(Long userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("토큰 정보와 일치하는 회원이 존재하지 않습니다."));
    }

    private void validateRefreshTokenWithUser(String refreshToken, User user) {
        String dbRefreshToken = user.getRefreshToken();

        if (dbRefreshToken == null || !dbRefreshToken.equals(refreshToken)) {
            throw new UnauthorizedException("유효하지 않은 JWT 토큰입니다.");
        }
    }

    @Transactional
    public void logout(User user) {
        user.setRefreshToken(null);
    }
}
