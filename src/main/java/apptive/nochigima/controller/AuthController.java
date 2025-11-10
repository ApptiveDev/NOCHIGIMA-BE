package apptive.nochigima.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import apptive.nochigima.dto.response.AuthResponse;
import apptive.nochigima.dto.response.AuthUriResponse;
import apptive.nochigima.service.AuthService;

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
}
