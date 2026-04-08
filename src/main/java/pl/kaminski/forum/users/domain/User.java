package pl.kaminski.forum.users.domain;

import jakarta.persistence.*;
import lombok.*;
import pl.kaminski.forum.commons.EntityId;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@Builder(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {

    @Id
    @AttributeOverride(name = "value", column = @Column(name = "user_id"))
    @Builder.Default
    private EntityId id = EntityId.newId();
    private UsernameVO username;
    private PasswordVO password;
    private FirstNameVO firstName;
    private LastNameVO lastName;
    private BirthDateVO birthdate;
    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDateTime creationDate;

}
