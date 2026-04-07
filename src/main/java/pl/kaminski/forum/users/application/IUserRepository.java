package pl.kaminski.forum.users.application;

import pl.kaminski.forum.commons.EntityId;
import pl.kaminski.forum.users.domain.User;
import pl.kaminski.forum.users.domain.UsernameVO;

import java.util.Optional;

public interface IUserRepository {
    Optional<EntityId> findIdByUsername(UsernameVO usernameVo);
    Optional<User> findByUsername(String username);
    void save(User user);
}
