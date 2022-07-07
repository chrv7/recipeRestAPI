package recipes.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.businesslayer.User;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
    User save(User user);
}
