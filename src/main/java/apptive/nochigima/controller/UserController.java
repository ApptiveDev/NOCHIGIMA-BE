package apptive.nochigima.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import apptive.nochigima.config.Auth;
import apptive.nochigima.domain.User;
import apptive.nochigima.service.UserService;

@Tag(name = "회원 API")
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/check-nickname")
    public ResponseEntity<Boolean> checkNickname(@RequestParam String nickname) {
        return ResponseEntity.ok(userService.checkNicknameDuplication(nickname));
    }

    @PatchMapping("/nickname")
    public ResponseEntity<Void> updateNickname(@Auth User user, @RequestBody Map<String, String> request) {
        userService.updateNickname(user.getId(), request.get("nickname"));
        return ResponseEntity.ok().build();
    }
}
