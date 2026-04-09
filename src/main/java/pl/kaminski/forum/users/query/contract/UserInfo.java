package pl.kaminski.forum.users.query.contract;

import pl.kaminski.forum.users.domain.Role;

import java.util.UUID;

public record UserInfo(
        UUID id,
        String username,
        String firstName,
        String lastName,
        Role role
) {
}
