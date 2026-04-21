package pl.kaminski.forum.users.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserRepositoryIntegrationTest {

    @Autowired
    UserConfiguration userConfiguration;

    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = userConfiguration.userRepository;
    }

    @Test
    void test() {
        userRepository.fdsafds
    }
}
