package pl.kaminski.forum.users.domain;

import java.util.Optional;

public interface IUserRepository {
    boolean existsByUsername(UsernameVO usernameVo);
    Optional<User> findByUsername(String username);
    void save(User user);
}
