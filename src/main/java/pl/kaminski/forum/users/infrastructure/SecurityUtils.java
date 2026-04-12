package pl.kaminski.forum.users.infrastructure;

import org.springframework.security.core.context.SecurityContextHolder;
import pl.kaminski.forum.users.application.contract.authentication.AuthenticatedUser;

public class SecurityUtils {
    public static AuthenticatedUser getAuthenticatedUser() {
        return (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
