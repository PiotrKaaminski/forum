package pl.kaminski.forum.users.application.contract.authentication;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.kaminski.forum.commons.EntityId;
import pl.kaminski.forum.users.domain.Role;

@RequiredArgsConstructor
@Getter
public class AuthenticatedUser {
    private final EntityId id;
    private final String username;
    private final Role role;
}
