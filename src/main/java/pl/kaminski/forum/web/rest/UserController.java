package pl.kaminski.forum.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.kaminski.forum.users.application.contract.authentication.IAuthenticationService;
import pl.kaminski.forum.users.application.contract.authentication.LoginUserResult;
import pl.kaminski.forum.users.application.contract.authentication.LoginUserRequest;
import pl.kaminski.forum.users.application.contract.RegisterUserRequest;
import pl.kaminski.forum.users.application.contract.IUserService;
import pl.kaminski.forum.users.application.contract.RegisterUserResult;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;
    private final IAuthenticationService userSecurityService;

    @PostMapping("/api/users")
    RegisterUserResult registerNewUser(@RequestBody RegisterUserRequest request) {
        return userService.registerNewUser(request);
    }

    @PostMapping("/api/login")
    public LoginUserResult login(@RequestBody LoginUserRequest request) {
        return userSecurityService.login(request);
    }
}
