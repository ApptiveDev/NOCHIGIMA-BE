package apptive.nochigima.client;

import org.springframework.http.MediaType;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;

import apptive.nochigima.config.properties.KakaoAuthProperties;
import apptive.nochigima.dto.response.KakaoAuthTokenResponse;
import apptive.nochigima.dto.response.KakaoUserInfoResponse;

@Component
@RequiredArgsConstructor
public class KakaoAuthClient {

    private final RestClient restClient;
    private final KakaoAuthProperties kakaoAuthProperties;

    @Retryable(retryFor = RuntimeException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000, multiplier = 2))
    public KakaoAuthTokenResponse getKakaoOAuthToken(String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoAuthProperties.clientId());
        params.add("client_secret", kakaoAuthProperties.clientSecret());
        params.add("redirect_uri", kakaoAuthProperties.redirectUri());
        params.add("code", code);

        return restClient
                .post()
                .uri(kakaoAuthProperties.tokenUri())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(params)
                .retrieve()
                .body(KakaoAuthTokenResponse.class);
    }

    @Retryable(retryFor = RuntimeException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000, multiplier = 2))
    public KakaoUserInfoResponse getKakaoUserInfo(KakaoAuthTokenResponse token) {
        return restClient
                .post()
                .uri(kakaoAuthProperties.infoUri())
                .header("Authorization", "Bearer " + token.accessToken())
                .retrieve()
                .body(KakaoUserInfoResponse.class);
    }

    @Retryable(retryFor = RuntimeException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000, multiplier = 2))
    public KakaoUserInfoResponse getKakaoUserInfoByAccessToken(String accessToken) {
        return restClient
                .get()
                .uri(kakaoAuthProperties.infoUri())
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .body(KakaoUserInfoResponse.class);
    }
}
