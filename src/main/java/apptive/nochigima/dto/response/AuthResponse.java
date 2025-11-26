package apptive.nochigima.dto.response;

public record AuthResponse(String accessToken, String refreshToken, boolean isNewMember) {}
