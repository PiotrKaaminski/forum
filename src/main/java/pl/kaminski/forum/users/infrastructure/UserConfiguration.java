package pl.kaminski.forum.users.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.kaminski.forum.commons.DateTimeProvider;
import pl.kaminski.forum.users.domain.IUserRepository;
import pl.kaminski.forum.users.application.AuthenticationService;
import pl.kaminski.forum.users.application.UserService;
import pl.kaminski.forum.users.application.contract.authentication.IAuthenticationService;
import pl.kaminski.forum.users.application.contract.IUserService;
import pl.kaminski.forum.users.application.JwtUtils;
import pl.kaminski.forum.users.domain.UserFactory;

public class UserConfiguration {

    private final IUserRepository userRepository;
    private final UserFactory userFactory;
    private final PasswordEncoder passwordEncoder;

    UserConfiguration(UserJpaRepository userJpaRepository, DateTimeProvider dateTimeProvider) {
        this.userRepository = new UserRepository(userJpaRepository);
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.userFactory = new UserFactory(userRepository, dateTimeProvider, passwordEncoder);

    }

    @Bean
    IUserService userService(DateTimeProvider dateTimeProvider) {
        return new UserService(userRepository, dateTimeProvider, userFactory);
    }

    @Bean
    IAuthenticationService userSecurityService(JwtUtils jwtUtils) {
        return new AuthenticationService(userRepository, passwordEncoder, jwtUtils);
    }

    @Bean
    JWTFilter jwtFilter(JwtUtils jwtUtils) {
        return new JWTFilter(jwtUtils, userRepository);
    }

    @Bean
    public JwtUtils jwtUtils(@Value("${jwt.secret}") String secretKey) {
        return new JwtUtils(secretKey);
    }
}
