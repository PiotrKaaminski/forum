package pl.kaminski.forum.users.application;

import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;
import pl.kaminski.forum.users.application.contract.RegisterUserRequest;
import pl.kaminski.forum.users.application.contract.IUserService;
import pl.kaminski.forum.users.application.contract.RegisterUserResult;
import pl.kaminski.forum.users.domain.IUserRepository;
import pl.kaminski.forum.users.domain.User;
import pl.kaminski.forum.users.domain.UserFactory;

import java.util.Optional;

@RequiredArgsConstructor
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final UserFactory userFactory;

    @Override
    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public RegisterUserResult registerNewUser(RegisterUserRequest request) {
        Assert.notNull(request, "Request cannot be null");
        var createUserResult = userFactory.createNewUser(request);
        if (createUserResult.isError()) {
            return RegisterUserResult.fromValidationError(createUserResult.getError());
        }
        var user = createUserResult.getSuccess();
        userRepository.save(user);
        return RegisterUserResult.success(user.getId().value());
    }

}
