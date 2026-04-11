package pl.kaminski.forum.users.domain;

import jakarta.persistence.PostLoad;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.kaminski.forum.commons.DateTimeProvider;
import pl.kaminski.forum.commons.EntityId;
import pl.kaminski.forum.commons.Specification;
import pl.kaminski.forum.commons.result.Result;
import pl.kaminski.forum.users.application.contract.RegisterUserRequest;
import pl.kaminski.forum.users.application.contract.RegisterUserResult;

import java.util.Optional;
import java.util.function.Function;

public class UserFactory {

    private final IUserRepository userRepository;
    private static DateTimeProvider dateTimeProvider;
    private final PasswordEncoder passwordEncoder;
    private static Function<EntityId, IPasswordHistory> passwordHistoryProvider;

    public UserFactory(IUserRepository userRepository, DateTimeProvider dateTimeProvider, PasswordEncoder passwordEncoder, Function<EntityId, IPasswordHistory> passwordHistoryProvider) {
        this.userRepository = userRepository;
        UserFactory.dateTimeProvider = dateTimeProvider;
        this.passwordEncoder = passwordEncoder;
        UserFactory.passwordHistoryProvider = passwordHistoryProvider;
    }

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

        var userId = EntityId.newId();
        var user = userBuilder
                .id(userId)
                .creationDate(dateTimeProvider.currentDateTime())
                .build();
        injectUserDependencies(user);
        return Result.success(user);
    }

    private static void injectUserDependencies(User user) {
        user.setPasswordHistory(passwordHistoryProvider.apply(user.getId()));
        user.setDateTimeProvider(dateTimeProvider);
    }

    private Specification<UsernameVO> isUsernameNotUnique() {
        return userRepository::existsByUsername;
    }

    static class UserInjector {
        @PostLoad
        void postLoad(User user) {
            injectUserDependencies(user);
        }
    }

}
