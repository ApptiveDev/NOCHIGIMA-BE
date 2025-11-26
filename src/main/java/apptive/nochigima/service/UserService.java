package apptive.nochigima.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import apptive.nochigima.domain.User;
import apptive.nochigima.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    public boolean checkNicknameDuplication(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    @Transactional
    public void updateNickname(Long userId, String newNickname) {
        if (checkNicknameDuplication(newNickname)) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        user.updateNickname(newNickname);
    }
}
