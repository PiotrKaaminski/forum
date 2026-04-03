package pl.kaminski.forum.api.users;

import pl.kaminski.forum.users.domain.Role;

import java.time.LocalDate;

public record RegisterUserRequest(String username, String password, Role role,
                                  String firstName, String lastName, LocalDate birthDate) {
}
