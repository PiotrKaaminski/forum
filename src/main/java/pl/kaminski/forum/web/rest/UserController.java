package pl.kaminski.forum.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.kaminski.forum.users.application.security.contract.IUserSecurityService;
import pl.kaminski.forum.users.application.security.contract.LoginUserResult;
import pl.kaminski.forum.users.domain.security.contract.LoginUserRequest;
import pl.kaminski.forum.users.application.contract.RegisterUserRequest;
import pl.kaminski.forum.users.application.contract.IUserService;
import pl.kaminski.forum.users.application.contract.RegisterUserResult;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;
    private final IUserSecurityService userSecurityService;

    @PostMapping("/api/user")
    RegisterUserResult registerNewUser(@RequestBody RegisterUserRequest request) {
        return userService.registerNewUser(request);
    }

    @PostMapping("/login")
    public LoginUserResult login(@RequestBody LoginUserRequest request) {
        return userSecurityService.login(request);
    }
}
