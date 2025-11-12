package apptive.nochigima.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KakaoUserInfoResponse(@JsonProperty("id") String oauthId) {}
