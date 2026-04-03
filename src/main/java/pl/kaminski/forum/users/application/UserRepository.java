package pl.kaminski.forum.users.application;

import pl.kaminski.forum.users.domain.User;

public interface UserRepository {
    Boolean isUsernameNotUnique(String username);
    void save(User user);
}
