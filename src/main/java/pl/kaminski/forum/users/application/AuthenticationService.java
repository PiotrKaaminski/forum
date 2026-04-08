package pl.kaminski.forum.users.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.kaminski.forum.users.application.contract.authentication.IAuthenticationService;
import pl.kaminski.forum.users.application.contract.authentication.LoginUserResult;
import pl.kaminski.forum.users.application.contract.authentication.LoginUserRequest;
import pl.kaminski.forum.users.domain.IUserRepository;

@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public LoginUserResult login(LoginUserRequest request) {

        var userOptional = userRepository.findByUsername(request.username());
        if (userOptional.isEmpty()) {
            return LoginUserResult.badCredentials();
        }
        var user = userOptional.get();
        if (user.passwordDoesNotMatch(request.password(), passwordEncoder)) {
            return LoginUserResult.badCredentials();
        }
        var jwtToken = jwtUtils.generateToken(user.getUsername());
        return LoginUserResult.success(jwtToken);

    }
}
