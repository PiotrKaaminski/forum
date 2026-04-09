package pl.kaminski.forum.users.query;

import pl.kaminski.forum.users.domain.User;

import java.util.Optional;

public interface IUserQueryRepository {

    Optional<User> findByUsername(String username);

}
