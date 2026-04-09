package pl.kaminski.forum.users.query;

import jakarta.persistence.*;
import lombok.Data;
import pl.kaminski.forum.users.domain.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "UserQuery")
@Table(name = "users")
@Data
public class User {
    @Id
    @Column(name = "user_id")
    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
    private LocalDate birthdate;
    private LocalDateTime creationDate;
    @Enumerated(EnumType.STRING)
    private Role role;
}
