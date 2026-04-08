package pl.kaminski.forum.users.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.kaminski.forum.commons.DateTimeProvider;
import pl.kaminski.forum.commons.Specification;
import pl.kaminski.forum.commons.result.Result;
import pl.kaminski.forum.users.application.contract.RegisterUserRequest;
import pl.kaminski.forum.users.application.contract.RegisterUserResult;

import java.util.Optional;

@RequiredArgsConstructor
public class UserFactory {

    private final IUserRepository userRepository;
    private final DateTimeProvider dateTimeProvider;
    private final PasswordEncoder passwordEncoder;

    public Result<User, RegisterUserResult.ValidationError> createNewUser(RegisterUserRequest request) {
        var validationErrorBuilder = RegisterUserResult.errorBuilder();
        var userBuilder = User.builder();

        UsernameVO.create(request.username()).handle(
                username -> isUsernameNotUnique().isSatisfiedBy(username, validationErrorBuilder::withUsernameNotUnique, userBuilder::username),
                validationErrorBuilder::withUsernameVoError
        );
        PasswordVO.create(request.password(), passwordEncoder).handle(userBuilder::password, validationErrorBuilder::withPasswordVoError);
        FirstNameVO.create(request.firstName()).handle(userBuilder::firstName, validationErrorBuilder::withFirstNameVoError);
        LastNameVO.create(request.lastName()).handle(userBuilder::lastName, validationErrorBuilder::withLastNameVoError);
        BirthDateVO.create(request.birthDate()).handle(userBuilder::birthdate, validationErrorBuilder::withBirthDateVoError);
        Optional.ofNullable(request.role()).ifPresentOrElse(userBuilder::role, validationErrorBuilder::withEmptyRole);

        if (validationErrorBuilder.hasViolations()) {
            return Result.error(validationErrorBuilder.build());
        }

        var user = userBuilder.creationDate(dateTimeProvider.currentDateTime()).build();
        return Result.success(user);
    }

    private Specification<UsernameVO> isUsernameNotUnique() {
        return userRepository::existsByUsername;
    }

}
