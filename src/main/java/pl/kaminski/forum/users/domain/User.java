package pl.kaminski.forum.users.domain;

import jakarta.persistence.*;
import lombok.Data;
import pl.kaminski.forum.commons.EntityId;

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
    private String password;
    @Embedded
    private PersonalInfoVO personalInfo;
    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDateTime creationDate;

    //metoda ma zwracać nowe result.
    public static User create(String username, String password, PersonalInfoVO personalInfo, Role role) {
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
}
