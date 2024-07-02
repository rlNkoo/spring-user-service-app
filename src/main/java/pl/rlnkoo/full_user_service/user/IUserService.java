package pl.rlnkoo.full_user_service.user;



import pl.rlnkoo.full_user_service.registration.RegistrationRequest;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<User> getAllUsers();

    User registerUser(RegistrationRequest registrationRequest);

    User findByEmail(String email);

    Optional<User> findById(Long id);

    void updateUser(Long id, String firstName, String lastName, String email);

    void deleteUser(Long id);
}
