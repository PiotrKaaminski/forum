package pl.kaminski.forum.users.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.kaminski.forum.commons.DateTimeProvider;
import pl.kaminski.forum.users.application.IUserRepository;
import pl.kaminski.forum.users.application.AuthenticationService;
import pl.kaminski.forum.users.application.UserService;
import pl.kaminski.forum.users.application.contract.authentication.IAuthenticationService;
import pl.kaminski.forum.users.application.contract.IUserService;
import pl.kaminski.forum.users.application.JwtUtils;

public class UserConfiguration {

    private final IUserRepository userRepository;

    UserConfiguration(UserJpaRepository userJpaRepository) {
        this.userRepository = new UserRepository(userJpaRepository);
    }

    @Bean
    IUserService userService(DateTimeProvider dateTimeProvider) {
        return new UserService(userRepository, dateTimeProvider);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    IAuthenticationService userSecurityService(PasswordEncoder bCryptPasswordEncoder, JwtUtils jwtUtils) {
        return new AuthenticationService(userRepository, bCryptPasswordEncoder, jwtUtils);
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
