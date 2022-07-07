package recipes.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import recipes.businesslayer.User;
import recipes.businesslayer.UserService;

import javax.validation.Valid;

@RestController
@Validated
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/api/register")
    public ResponseEntity<?> saveUser(@Valid @RequestBody User user) {
        if (userService.findUser(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().build();
        } else {
            user.setPassword(encoder.encode(user.getPassword()));
            userService.saveUser(user);
            return ResponseEntity.ok().build();
        }
    }

}
