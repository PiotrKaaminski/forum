package pl.kaminski.forum.users.domain;

import pl.kaminski.forum.commons.EntityId;

import java.util.Optional;

public interface IUserRepository {
    boolean existsByUsername(UsernameVO usernameVo);
    Optional<User> findByUsername(String username);
    Optional<EntityId> findIdByUsername(String username);
    void save(User user);
}
