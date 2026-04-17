package pl.kaminski.forum.users.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.kaminski.forum.users.domain.IUserRepository;
import pl.kaminski.forum.users.domain.UserFactory;

import static org.mockito.Mockito.*;

public class UserServiceUnitTests {

    private IUserRepository userRepositoryMock;
    private UserFactory userFactoryMock;

    private UserService userService;

    @BeforeEach
    void init() {
        userRepositoryMock = mock(IUserRepository.class);
        userFactoryMock = mock(UserFactory.class);
        userService = new UserService(userRepositoryMock, userFactoryMock);
    }

    @Test
    void test() {
    }

}
