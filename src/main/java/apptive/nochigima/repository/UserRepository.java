package apptive.nochigima.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import apptive.nochigima.domain.AuthProvider;
import apptive.nochigima.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByOauthIdAndAuthProvider(String oauthId, AuthProvider authProvider);
}
