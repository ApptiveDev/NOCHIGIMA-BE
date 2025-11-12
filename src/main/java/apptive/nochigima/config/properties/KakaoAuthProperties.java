package apptive.nochigima.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("auth.oauth2.kakao")
public record KakaoAuthProperties(
        String clientId, String clientSecret, String authUri, String redirectUri, String tokenUri, String infoUri) {}
