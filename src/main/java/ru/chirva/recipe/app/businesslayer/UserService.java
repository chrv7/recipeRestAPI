package ru.chirva.recipe.app.businesslayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chirva.recipe.app.persistence.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findUser(String email) {
        if (userRepository.findUserByEmail(email).isPresent()) {
            return Optional.of(userRepository.findUserByEmail(email).get());
        } else {
            return Optional.empty();
        }
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}
