package apptive.nochigima.service;

import static apptive.nochigima.domain.AuthProvider.GOOGLE;
import static apptive.nochigima.domain.AuthProvider.KAKAO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import apptive.nochigima.client.GoogleAuthClient;
import apptive.nochigima.client.KakaoAuthClient;
import apptive.nochigima.config.properties.GoogleAuthProperties;
import apptive.nochigima.config.properties.KakaoAuthProperties;
import apptive.nochigima.domain.User;
import apptive.nochigima.dto.response.AuthResponse;
import apptive.nochigima.dto.response.AuthUriResponse;
import apptive.nochigima.dto.response.GoogleAuthTokenResponse;
import apptive.nochigima.dto.response.GoogleUserInfoResponse;
import apptive.nochigima.dto.response.KakaoAuthTokenResponse;
import apptive.nochigima.dto.response.KakaoUserInfoResponse;
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

        User user = userRepository
                .findByOauthIdAndAuthProvider(userInfo.oauthId(), KAKAO)
                .orElseGet(() -> userRepository.save(new User(userInfo.oauthId(), KAKAO)));

        String jwt = jwtUtil.createToken(user);

        return new AuthResponse(jwt);
    }

    @Transactional
    public AuthResponse googleLogin(String code) {
        GoogleAuthTokenResponse token = googleAuthClient.getGoogleOAuthToken(code);
        GoogleUserInfoResponse userInfo = googleAuthClient.getGoogleUserInfo(token);

        User user = userRepository
                .findByOauthIdAndAuthProvider(userInfo.oauthId(), GOOGLE)
                .orElseGet(() -> userRepository.save(new User(userInfo.oauthId(), GOOGLE)));

        String jwt = jwtUtil.createToken(user);

        return new AuthResponse(jwt);
    }
}
