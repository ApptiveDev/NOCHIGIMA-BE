package apptive.nochigima.client;

import org.springframework.http.MediaType;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;

import apptive.nochigima.config.properties.GoogleAuthProperties;
import apptive.nochigima.dto.response.GoogleAuthTokenResponse;
import apptive.nochigima.dto.response.GoogleUserInfoResponse;

@Component
@RequiredArgsConstructor
public class GoogleAuthClient {

    private final RestClient restClient;
    private final GoogleAuthProperties googleAuthPrpoerties;

    @Retryable(retryFor = RuntimeException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000, multiplier = 2))
    public GoogleAuthTokenResponse getGoogleOAuthToken(String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", googleAuthPrpoerties.clientId());
        params.add("client_secret", googleAuthPrpoerties.clientSecret());
        params.add("redirect_uri", googleAuthPrpoerties.redirectUri());
        params.add("code", code);

        return restClient
                .post()
                .uri(googleAuthPrpoerties.tokenUri())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(params)
                .retrieve()
                .body(GoogleAuthTokenResponse.class);
    }

    @Retryable(retryFor = RuntimeException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000, multiplier = 2))
    public GoogleUserInfoResponse getGoogleUserInfo(GoogleAuthTokenResponse token) {
        return restClient
                .post()
                .uri(googleAuthPrpoerties.infoUri())
                .header("Authorization", "Bearer " + token.accessToken())
                .retrieve()
                .body(GoogleUserInfoResponse.class);
    }
}
