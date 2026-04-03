package pl.kaminski.forum.users.domain;

import jakarta.persistence.*;
import lombok.*;
import pl.kaminski.forum.commons.DateTimeProvider;
import pl.kaminski.forum.commons.EntityId;
import pl.kaminski.forum.commons.result.Result;
import pl.kaminski.forum.users.application.contract.RegisterUserRequest;
import pl.kaminski.forum.users.application.contract.RegisterUserResult;

import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Data
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {

    @Id
    @AttributeOverride(name = "value", column = @Column(name = "user_id"))
    private EntityId id;
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "username"))
    private UsernameVO username;
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "password"))
    private PasswordVO password;
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "first_name"))
    private FirstNameVO firstName;
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "last_name"))
    private LastNameVO lastName;
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "birthdate"))
    private BirthDateVO birthdate;
    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDateTime creationDate;

    public static Result<User, RegisterUserResult.ValidationError> createFromRequest(RegisterUserRequest request, DateTimeProvider dateTimeProvider, Boolean notUnique) {
        var validationErrorBuilder = RegisterUserResult.errorBuilder();
        var userBuilder = User.builder();

        UsernameVO.create(request.username(), notUnique).process(userBuilder::username, validationErrorBuilder::withUsernameVoError);
        PasswordVO.create(request.password()).process(userBuilder::password, validationErrorBuilder::withPasswordVoError);
        FirstNameVO.create(request.firstName()).process(userBuilder::firstName, validationErrorBuilder::withFirstNameVoError);
        LastNameVO.create(request.lastName()).process(userBuilder::lastName, validationErrorBuilder::withLastNameVoError);
        BirthDateVO.create(request.birthDate()).process(userBuilder::birthdate, validationErrorBuilder::withBirthDateVoError);
        Optional.ofNullable(request.role()).ifPresentOrElse(userBuilder::role, validationErrorBuilder::withEmptyRole);

        if (validationErrorBuilder.hasViolations()) {
            return Result.error(validationErrorBuilder.build());
        }

        var newUser = userBuilder.id(EntityId.newId())
                .creationDate(dateTimeProvider.currentDateTime())
                .build();

        return Result.success(newUser);
    }
}
