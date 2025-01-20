package IC.SmartPark.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);

    void deleteById(int id);
    ArrayList<User>findByRole(Role role);
}
