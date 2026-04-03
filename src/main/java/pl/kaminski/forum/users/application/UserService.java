package pl.kaminski.forum.users.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kaminski.forum.users.application.contract.RegisterUserRequest;
import pl.kaminski.forum.users.application.contract.IUserService;
import pl.kaminski.forum.users.application.contract.RegisterUserResult;
import pl.kaminski.forum.users.domain.User;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;

    public RegisterUserResult registerNewUser(RegisterUserRequest request) {
        var createUserResult = User.createFromRequest(request);
        if (createUserResult.isError()) {
            return RegisterUserResult.fromValidationError(createUserResult.getError());
        }
        var user = createUserResult.getSuccess();

        userRepository.save(user);
        return RegisterUserResult.success(user.getId().value());
    }
}
