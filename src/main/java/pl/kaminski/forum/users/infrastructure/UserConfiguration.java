package pl.kaminski.forum.users.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.kaminski.forum.commons.DateTimeProvider;
import pl.kaminski.forum.commons.EntityId;
import pl.kaminski.forum.users.application.AuthenticationService;
import pl.kaminski.forum.users.application.UserService;
import pl.kaminski.forum.users.application.contract.authentication.IAuthenticationService;
import pl.kaminski.forum.users.application.contract.IUserService;
import pl.kaminski.forum.users.application.JwtUtils;
import pl.kaminski.forum.users.domain.IPasswordHistory;
import pl.kaminski.forum.users.domain.IUserRepository;
import pl.kaminski.forum.users.domain.UserFactory;
import pl.kaminski.forum.users.query.IUserQueryRepository;
import pl.kaminski.forum.users.query.UserQueryFacade;
import pl.kaminski.forum.users.query.contract.IUserQueryFacade;

import java.util.function.Function;

public class UserConfiguration {

    private final IUserRepository userRepository;
    private final IUserQueryRepository userQueryRepository;
    private final UserFactory userFactory;
    private final PasswordEncoder passwordEncoder;

    UserConfiguration(UserJpaRepository userJpaRepository, UserQueryJpaRepository userQueryJpaRepository, DateTimeProvider dateTimeProvider,
                      JdbcTemplate jdbcTemplate) {
        this.userRepository = new UserRepository(userJpaRepository);
        this.userQueryRepository = new UserQueryRepository(userQueryJpaRepository);
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.userFactory = new UserFactory(userRepository, dateTimeProvider, passwordEncoder, passwordHistoryFactory(jdbcTemplate));

    }

    @Bean
    IUserService userService() {
        return new UserService(userRepository, userFactory);
    }

    @Bean
    IAuthenticationService userSecurityService(JwtUtils jwtUtils) {
        return new AuthenticationService(userRepository, passwordEncoder, jwtUtils);
    }

    @Bean
    IUserQueryFacade userQueryFacade() {
        return new UserQueryFacade(userQueryRepository);
    }

    @Bean
    JWTFilter jwtFilter(JwtUtils jwtUtils) {
        return new JWTFilter(jwtUtils, userQueryRepository);
    }

    @Bean
    JwtUtils jwtUtils(@Value("${jwt.secret}") String secretKey) {
        return new JwtUtils(secretKey);
    }

    private Function<EntityId, IPasswordHistory> passwordHistoryFactory(JdbcTemplate jdbcTemplate) {
        return userId -> new PasswordHistory(userId, jdbcTemplate);
    }
}
