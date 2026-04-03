package pl.kaminski.forum.users.infrastructure;

import org.springframework.context.annotation.Bean;
import pl.kaminski.forum.commons.DateTimeProvider;
import pl.kaminski.forum.users.application.UserRepository;
import pl.kaminski.forum.users.application.UserService;

public class UserConfiguration {

    @Bean
    UserRepository userRepository(UserJpaRepository userJpaRepository) {
        return new UserRepositoryImpl(userJpaRepository);
    }

    @Bean
    UserService userService(UserRepository userRepository, DateTimeProvider dateTimeProvider) {
        return new UserService(userRepository, dateTimeProvider);
    }
}
