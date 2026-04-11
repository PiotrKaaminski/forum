package pl.kaminski.forum.users.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.kaminski.forum.commons.DateTimeProvider;
import pl.kaminski.forum.commons.EntityId;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@Builder(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(UserFactory.UserInjector.class)
public class User {

    @Id
    @AttributeOverride(name = "value", column = @Column(name = "user_id"))
    private EntityId id;
    private UsernameVO username;
    private PasswordVO password;
    private FirstNameVO firstName;
    private LastNameVO lastName;
    private BirthDateVO birthdate;
    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDateTime creationDate;

    @Transient
    @Setter(AccessLevel.PACKAGE)
    private DateTimeProvider dateTimeProvider;
    @Transient
    @Setter(AccessLevel.PACKAGE)
    private IPasswordHistory passwordHistory;

    public boolean passwordDoesNotMatch(String password, PasswordEncoder passwordEncoder) {
        return !passwordEncoder.matches(password, this.password.getPassword());
    }
}
