package pl.kaminski.forum.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.kaminski.forum.security.UserSecurityService;
import pl.kaminski.forum.users.application.contract.RegisterUserRequest;
import pl.kaminski.forum.users.application.contract.IUserService;
import pl.kaminski.forum.users.application.contract.RegisterUserResult;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;
    private final UserSecurityService userSecurityService;

    @PostMapping("/user")
    RegisterUserResult registerNewUser(@RequestBody RegisterUserRequest request) {
        return userService.registerNewUser(request);
    }

    @GetMapping("/login")
    public String login() {
//        return userSecurityService.authenticate(username, password);
    }
}
