package pl.kaminski.forum.users.domain;

import pl.kaminski.forum.commons.EntityId;

import java.util.Optional;

public interface IUserRepository {
    Optional<EntityId> findIdByUsername(UsernameVO usernameVo);
    Optional<User> findByUsername(String username);
    void save(User user);
}
