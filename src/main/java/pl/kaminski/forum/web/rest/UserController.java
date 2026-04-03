package pl.kaminski.forum.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kaminski.forum.api.users.RegisterUserRequest;
import pl.kaminski.forum.users.application.UserService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    void registerNewUser(RegisterUserRequest request) {
        userService.registerNewUser(request);
    }
}
