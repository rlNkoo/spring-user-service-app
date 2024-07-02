package pl.rlnkoo.full_user_service.registration.token;

import pl.rlnkoo.full_user_service.user.User;

import java.util.Optional;

public interface IVerificationTokenService {

    String validateToken(String token);

    void saveVerifiactionTokenForUser(User user, String token);

    Optional<VerificationToken> findByToken(String token);

    void deleteUserToken(long id);
}
