package ru.chirva.recipe.app.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.chirva.recipe.app.businesslayer.User;
import ru.chirva.recipe.app.businesslayer.UserService;

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
