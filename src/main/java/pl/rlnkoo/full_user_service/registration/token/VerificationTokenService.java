package pl.rlnkoo.full_user_service.registration.token;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.rlnkoo.full_user_service.user.User;
import pl.rlnkoo.full_user_service.user.UserRepository;

import java.util.Calendar;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VerificationTokenService implements IVerificationTokenService {

    private final VerificationTokenRepository tokenRepository;
    private final UserRepository userRepository;

    @Override
    public String validateToken(String token) {

        Optional<VerificationToken> theToken = tokenRepository.findByToken(token);
        if (theToken.isEmpty()) {
            return "INVALID";
        }
        User user = theToken.get().getUser();
        Calendar calendar = Calendar.getInstance();
        if (theToken.get().getExpirationTime().getTime() - calendar.getTime().getTime() <= 0) {
            return "EXPIRED";
        }
        user.setEnabled(true);
        userRepository.save(user);
        return "VALID";
    }

    @Override
    public void saveVerifiactionTokenForUser(User user, String token) {
        var verificationToken = new VerificationToken(token, user);
        tokenRepository.save(verificationToken);

    }

    @Override
    public Optional<VerificationToken> findByToken(String token) {
        return tokenRepository.findByToken(token);
    }

    @Override
    public void deleteUserToken(long id) {
        tokenRepository.deleteByUserId(id);
    }
}
