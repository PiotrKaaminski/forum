package pl.kaminski.forum.users.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kaminski.forum.commons.EntityId;
import pl.kaminski.forum.users.domain.User;
import pl.kaminski.forum.users.domain.UsernameVO;

import java.util.Optional;

@Repository
interface UserJpaRepository extends JpaRepository<User, EntityId> {

    boolean existsByUsername(UsernameVO username);

    Optional<User> findByUsername_Username(String username);
}
