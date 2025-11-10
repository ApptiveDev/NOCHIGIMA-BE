package apptive.nochigima.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("auth.oauth2.google")
public record GoogleAuthProperties(
        String clientId, String clientSecret, String authUri, String redirectUri, String tokenUri, String infoUri) {}
