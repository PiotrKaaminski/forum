package pl.kaminski.forum.users.domain.security.contract;

public record LoginUserRequest(String username, String password) {
}
