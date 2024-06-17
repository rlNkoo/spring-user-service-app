package pl.rlnkoo.full_user_service.registration;

import lombok.Data;
import pl.rlnkoo.full_user_service.user.Role;

import java.util.List;

@Data
public class RegistrationRequest {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private List<Role> roles;
}
