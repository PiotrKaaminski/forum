package pl.kaminski.forum.users.application.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.kaminski.forum.users.application.UserRepository;
import pl.kaminski.forum.users.application.security.contract.IUserSecurityService;
import pl.kaminski.forum.users.application.security.contract.LoginUserResult;
import pl.kaminski.forum.users.domain.security.JwtUtils;
import pl.kaminski.forum.users.domain.security.contract.LoginUserRequest;

@RequiredArgsConstructor
public class UserSecurityService implements IUserSecurityService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public LoginUserResult login(LoginUserRequest request) {

        var user = userRepository.findByUsername(request.username());
        if (user.isEmpty()) {
            return LoginUserResult.badCredentials();
        }
//        if (!passwordEncoder.matches(request.password(), user.get().getPassword().getValue())) {
//            return LoginUserResult.badCredentials();
//        }
        if (!request.password().equals(user.get().getPassword().getValue())) {
            return LoginUserResult.badCredentials();
        }
        var JwtToken = "Bearer " + jwtUtils.generateToken(user.get().getUsername().getValue());
        return LoginUserResult.success(JwtToken);

    }
}
