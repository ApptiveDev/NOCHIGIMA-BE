package apptive.nochigima.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import apptive.nochigima.config.properties.GoogleAuthProperties;
import apptive.nochigima.config.properties.KakaoAuthProperties;
import apptive.nochigima.dto.response.AuthUriResponse;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final KakaoAuthProperties kakaoOAuthProperties;
    private final GoogleAuthProperties googleOAuthProperties;

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
}
