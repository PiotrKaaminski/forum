package pl.kaminski.forum.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.kaminski.forum.users.application.contract.IUserSecurityService;
import pl.kaminski.forum.users.domain.security.contract.LoginUserRequest;
import pl.kaminski.forum.users.application.contract.RegisterUserRequest;
import pl.kaminski.forum.users.application.contract.IUserService;
import pl.kaminski.forum.users.application.contract.RegisterUserResult;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;
    private final IUserSecurityService IUserSecurityService;

    @PostMapping("/api/user")
    RegisterUserResult registerNewUser(@RequestBody RegisterUserRequest request) {
        return userService.registerNewUser(request);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginUserRequest request) {
        // zwracane ma być nasze result.
        // normalna, nowa autentykacja. Nie ta springowa
        return null;
    }
}
