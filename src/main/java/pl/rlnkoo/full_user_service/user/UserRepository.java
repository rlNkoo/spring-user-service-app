package pl.rlnkoo.full_user_service.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Modifying
    @Query(value = "UPDATE User u SET u.firstName =:firstName, u.lastName=:lastName, " +
            "u.email =:email WHERE u.id =:id")
    void update(String firstName, String lastName, String email, Long id);
}
