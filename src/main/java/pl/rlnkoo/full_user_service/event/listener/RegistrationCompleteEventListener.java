package pl.rlnkoo.full_user_service.event.listener;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import pl.rlnkoo.full_user_service.event.RegistrationCompleteEvent;
import pl.rlnkoo.full_user_service.registration.token.VerificationTokenService;
import pl.rlnkoo.full_user_service.user.User;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    private final VerificationTokenService tokenService;

    private final JavaMailSender mailSender;

    private User user;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        // 1. Get the user
        user = event.getUser();

        // 2. Generate a token for the user
        String vToken = UUID.randomUUID().toString();

        // 3. Save the token for the user
        tokenService.saveVerifiactionTokenForUser(user, vToken);

        // 4. Build the verification URL
        String url = event.getConfirmationUrl() + "/registration/verifyEmail?token" + vToken;

        // 5. Send the email to the user
        try {
            sendVerificationEmail(url);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException {
        String subject = "Email Verification";

        String senderName = "User Registration Portal Service";

        String mailContent = "<p> Hi, "+ user.getFirstName()+ ", </p>"+
                "<p>Thank you for registering with us,"+"" +
                "Please, follow the link below to complete your registration.</p>"+
                "<a href=\"" +url+ "\">Verify your email to activate your account</a>"+
                "<p> Thank you <br> Users Registration Portal Service";
        emailMassage(subject, senderName, mailContent, mailSender, user);
    }

    private static void emailMassage(String subject, String senderName, String mailContent,
                                     JavaMailSender mailSender, User user)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("rolakartur96@gmail.com", senderName);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }
}
