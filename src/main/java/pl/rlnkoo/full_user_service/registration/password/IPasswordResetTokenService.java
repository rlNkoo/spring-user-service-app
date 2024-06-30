package pl.rlnkoo.full_user_service.registration.password;

public interface IPasswordResetTokenService {
    String validatePasswordResetToken(String theToken);
}
