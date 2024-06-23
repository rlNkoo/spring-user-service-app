package pl.rlnkoo.full_user_service.registration;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.rlnkoo.full_user_service.event.RegistrationCompleteEvent;
import pl.rlnkoo.full_user_service.registration.token.VerificationToken;
import pl.rlnkoo.full_user_service.registration.token.VerificationTokenService;
import pl.rlnkoo.full_user_service.user.IUserService;
import pl.rlnkoo.full_user_service.user.User;
import pl.rlnkoo.full_user_service.utility.UrlUtil;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/registration")
public class RegistrationController {

    private final IUserService userService;

    private final ApplicationEventPublisher publisher;

    private final VerificationTokenService tokenService;

    @GetMapping("/registration-form")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new RegistrationRequest());

        return "registration";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") RegistrationRequest registration, HttpServletRequest request) {
        User user = userService.registerUser(registration);
        publisher.publishEvent(new RegistrationCompleteEvent(user, UrlUtil.getApplicationUrl(request)));

        return "redirect:/registration/registration-form?success";
    }

    public String verifyEmail(@RequestParam("token") String token) {
        Optional<VerificationToken> theToken = tokenService.findByToken(token);
        if (theToken.isPresent() && theToken.get().getUser().isEnabled()) {
            return "redirect:/login?verified";
        }
        String verificationResult = tokenService.validateToken(String.valueOf(theToken));
        if (verificationResult.equalsIgnoreCase("invalid")) {
            return "redirect:/error?invalid";
        } else if (verificationResult.equalsIgnoreCase("expired")) {
            return "redirect:/error?expired";
        } else if (verificationResult.equalsIgnoreCase("valid")) {
            return "redirect:/login?valid";
        }
        return "redirect:/error?invalid";

    }
}

