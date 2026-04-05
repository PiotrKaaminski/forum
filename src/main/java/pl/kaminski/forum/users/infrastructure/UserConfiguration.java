package pl.kaminski.forum.users.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.kaminski.forum.commons.DateTimeProvider;
import pl.kaminski.forum.users.application.UserRepository;
import pl.kaminski.forum.users.application.security.UserSecurityService;
import pl.kaminski.forum.users.application.UserService;
import pl.kaminski.forum.users.application.security.contract.IUserSecurityService;
import pl.kaminski.forum.users.application.contract.IUserService;
import pl.kaminski.forum.users.domain.security.JwtUtils;

public class UserConfiguration {

    @Bean
    UserRepository userRepository(UserJpaRepository userJpaRepository) {
        return new UserRepositoryImpl(userJpaRepository);
    }

    @Bean
    IUserService userService(UserRepository userRepository, DateTimeProvider dateTimeProvider) {
        return new UserService(userRepository, dateTimeProvider);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    IUserSecurityService userSecurityService(UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder, JwtUtils jwtUtils) {
        return new UserSecurityService(userRepository, bCryptPasswordEncoder, jwtUtils);
    }

    @Bean
    public JwtUtils jwtUtils(@Value( "${jwt.secret}") String secretKey) {
        return new JwtUtils(secretKey);
    }
}
