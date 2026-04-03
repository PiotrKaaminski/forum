package pl.kaminski.forum.users.application;

public interface UserRepository {
    Boolean checkUsernameUniqueness(String username);
}
