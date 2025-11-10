package apptive.nochigima.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import apptive.nochigima.config.Auth;
import apptive.nochigima.domain.User;
import apptive.nochigima.dto.request.ReissueRequest;
import apptive.nochigima.dto.response.AuthResponse;
import apptive.nochigima.dto.response.AuthUriResponse;
import apptive.nochigima.service.AuthService;

@Tag(name = "인증 API")
@RestController
@RequestMapping("v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/kakao")
    public AuthUriResponse getKakaoAuthUri() {
        return authService.getKakaoAuthUri();
    }

    @GetMapping("/google")
    public AuthUriResponse getGoogleAuthUri() {
        return authService.getGoogleAuthUri();
    }

    @GetMapping("/kakao/callback")
    public ResponseEntity<AuthResponse> kakaoOAuthCallback(@RequestParam String code) {
        AuthResponse authResponse = authService.kakaoLogin(code);
        return ResponseEntity.ok(authResponse);
    }

    @GetMapping("/google/callback")
    public ResponseEntity<AuthResponse> googleOAuthCallback(@RequestParam String code) {
        AuthResponse authResponse = authService.googleLogin(code);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/reissue")
    public ResponseEntity<AuthResponse> reissueTokens(@RequestBody ReissueRequest reissueRequest) {
        AuthResponse authResponse = authService.reissueTokens(reissueRequest.refreshToken());
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Parameter(hidden = true) @Auth User user) {
        authService.logout(user);
        return ResponseEntity.ok().build();
    }
}
