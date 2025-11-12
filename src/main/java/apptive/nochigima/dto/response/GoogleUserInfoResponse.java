package apptive.nochigima.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GoogleUserInfoResponse(@JsonProperty("sub") String oauthId) {}
