package pl.kaminski.forum.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.kaminski.forum.users.application.contract.authentication.IAuthenticationService;
import pl.kaminski.forum.users.application.contract.authentication.LoginUserResult;
import pl.kaminski.forum.users.application.contract.authentication.LoginUserRequest;
import pl.kaminski.forum.users.application.contract.RegisterUserRequest;
import pl.kaminski.forum.users.application.contract.IUserService;
import pl.kaminski.forum.users.application.contract.RegisterUserResult;
import pl.kaminski.forum.users.query.contract.IUserQueryFacade;
import pl.kaminski.forum.users.query.contract.UserInfo;

import java.security.Principal;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;
    private final IAuthenticationService userSecurityService;
    private final IUserQueryFacade userQueryFacade;

    @PostMapping("/users")
    RegisterUserResult registerNewUser(@RequestBody RegisterUserRequest request) {
        return userService.registerNewUser(request);
    }

    @PostMapping("/login")
    public LoginUserResult login(@RequestBody LoginUserRequest request) {
        return userSecurityService.login(request);
    }

    @GetMapping("/users/me")
    public UserInfo getCurrentUser(Principal principal) {
        return userQueryFacade.getMe(principal.getName());
    }

}
