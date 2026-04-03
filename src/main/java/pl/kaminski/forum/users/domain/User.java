package pl.kaminski.forum.users.domain;

import jakarta.persistence.*;
import lombok.Data;
import pl.kaminski.forum.commons.EntityId;
import pl.kaminski.forum.commons.result.Result;
import pl.kaminski.forum.users.application.contract.RegisterUserRequest;
import pl.kaminski.forum.users.application.contract.RegisterUserResult;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Data
public class User {

    @Id
    @AttributeOverride(name = "value", column = @Column(name = "user_id"))
    private EntityId id;
    @Embedded
    private UsernameVO username;
    private PasswordVO password;
    @Embedded
    private PersonalInfoVO personalInfo;
    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDateTime creationDate;

    //metoda ma zwracać nowe result.
    public static User create(UsernameVO username, PasswordVO password, PersonalInfoVO personalInfo, Role role) {
        // każde pole na VO i w nim walidacja z nowym result
        var newUser = new User();
        newUser.setId(EntityId.newId());
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setPersonalInfo(personalInfo);
        newUser.setRole(role);
        newUser.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return newUser;
    }

    //metoda ma zwracać nowe result.
    public static Result<User, RegisterUserResult.ValidationError> createFromRequest(RegisterUserRequest request) {
        var validationErrorBuilder = RegisterUserResult.errorBuilder();
        var personalInfo = PersonalInfoVO.create(request.firstName(), request.lastName(), request.birthDate());
        var usernameVOResult = UsernameVO.create(request.username());
        validationErrorBuilder.withUsernameVoResult(usernameVOResult);

        var passwordVOResult = PasswordVO.create(request.password());
        validationErrorBuilder.withPasswordVoResult(passwordVOResult);
        if (request.role() == null) {
            validationErrorBuilder.withEmptyRole();
        }
        if (validationErrorBuilder.hasViolations()) {
            return Result.error(validationErrorBuilder.build());
        }
        // każde pole na VO i w nim walidacja z nowym result
        var newUser = new User();
        newUser.setId(EntityId.newId());
        newUser.setUsername(usernameVOResult.getSuccess());
        newUser.setPassword(passwordVOResult.getSuccess());
        newUser.setPersonalInfo(personalInfo);
        newUser.setRole(request.role());
        newUser.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return Result.success(newUser);
    }
}
