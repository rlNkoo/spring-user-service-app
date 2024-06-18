package pl.rlnkoo.full_user_service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.rlnkoo.full_user_service.registration.RegistrationRequest;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User registerUser(RegistrationRequest registration) {
        var user = new User(registration.getFirstName(), registration.getLastName(),
                registration.getEmail(), passwordEncoder.encode(registration.getPassword()),
                Arrays.asList(new Role("ROLE_USER")));
        return user;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).
                orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
