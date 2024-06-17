package pl.rlnkoo.full_user_service.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import pl.rlnkoo.full_user_service.user.User;

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {

    private User user;

    private String confirmationUrl;

    public RegistrationCompleteEvent(User user, String confirmationUrl) {
        super(user);

        this.user = user;
        this.confirmationUrl = confirmationUrl;
    }
}
