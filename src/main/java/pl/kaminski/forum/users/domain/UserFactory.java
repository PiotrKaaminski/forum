package pl.kaminski.forum.users.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.kaminski.forum.commons.DateTimeProvider;
import pl.kaminski.forum.commons.result.Result;
import pl.kaminski.forum.users.application.contract.RegisterUserRequest;
import pl.kaminski.forum.users.application.contract.RegisterUserResult;

import java.util.Optional;


@RequiredArgsConstructor
public class UserFactory {

    private final IUserRepository userRepository;
    private final DateTimeProvider dateTimeProvider;
    private final PasswordEncoder bCryptPasswordEncoder;

    public Result<User, RegisterUserResult.ValidationError> createNewUser(RegisterUserRequest request) {
        var validationErrorBuilder = RegisterUserResult.errorBuilder();
        var userBuilder = User.builder();

        UsernameVO.create(request.username()).process(userBuilder::username, validationErrorBuilder::withUsernameVoError);
        PasswordVO.create(request.password()).process(userBuilder::password, validationErrorBuilder::withPasswordVoError);
        FirstNameVO.create(request.firstName()).process(userBuilder::firstName, validationErrorBuilder::withFirstNameVoError);
        LastNameVO.create(request.lastName()).process(userBuilder::lastName, validationErrorBuilder::withLastNameVoError);
        BirthDateVO.create(request.birthDate()).process(userBuilder::birthdate, validationErrorBuilder::withBirthDateVoError);
        Optional.ofNullable(request.role()).ifPresentOrElse(userBuilder::role, validationErrorBuilder::withEmptyRole);

        if (validationErrorBuilder.hasViolations()) {
            return Result.error(validationErrorBuilder.build());
        }

        var newUser = userBuilder.creationDate(dateTimeProvider.currentDateTime()).build();

        return Result.success(newUser);
    }

}
