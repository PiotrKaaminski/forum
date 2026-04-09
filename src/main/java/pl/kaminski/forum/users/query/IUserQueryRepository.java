package pl.kaminski.forum.users.query;

import java.util.Optional;

public interface IUserQueryRepository {

    Optional<UserQuery> findByUsername(String username);

}
