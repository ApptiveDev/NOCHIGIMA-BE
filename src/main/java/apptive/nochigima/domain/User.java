package apptive.nochigima.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "oauth_id")
    @NotNull private String oauthId;

    @Column(name = "auth_provider")
    @Enumerated(EnumType.STRING)
    @NotNull private AuthProvider authProvider;

    @Column(name = "nickname")
    @Nullable private String nickname;

    @Setter
    @Column(name = "refresh_token")
    @Nullable private String refreshToken;

    public User(String oauthId, AuthProvider authProvider) {
        this.oauthId = oauthId;
        this.authProvider = authProvider;
    }

    // 닉네임 변경 메서드
    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }
}
