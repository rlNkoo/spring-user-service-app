package pl.rlnkoo.full_user_service.registration.token;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.rlnkoo.full_user_service.user.User;

import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String token;

    private Date expirationTime;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public VerificationToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.expirationTime = TokenExpirationTime.getExpirationTime();
    }
}
