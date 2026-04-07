package pl.kaminski.forum.users.application;

import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;
import pl.kaminski.forum.commons.DateTimeProvider;
import pl.kaminski.forum.users.application.contract.RegisterUserRequest;
import pl.kaminski.forum.users.application.contract.IUserService;
import pl.kaminski.forum.users.application.contract.RegisterUserResult;
import pl.kaminski.forum.users.domain.User;

@RequiredArgsConstructor
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final DateTimeProvider dateTimeProvider;

    public RegisterUserResult registerNewUser(RegisterUserRequest request) {
        Assert.notNull(request, "Request cannot be null");
        var createUserResult = User.createFromRequest(request, dateTimeProvider);
        if (createUserResult.isError()) {
            return RegisterUserResult.fromValidationError(createUserResult.getError());
        }
        var user = createUserResult.getSuccess();
        var existingUserId = userRepository.findIdByUsername(user.getUsername());
        if (existingUserId.isPresent()) {
            return RegisterUserResult.usernameNotUnique(existingUserId.get());
        }
        userRepository.save(user);
        return RegisterUserResult.success(user.getId().value());
    }
}
