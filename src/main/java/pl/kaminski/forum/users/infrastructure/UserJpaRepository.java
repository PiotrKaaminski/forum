package pl.kaminski.forum.users.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.kaminski.forum.commons.EntityId;
import pl.kaminski.forum.users.domain.User;
import pl.kaminski.forum.users.domain.UsernameVO;

import java.util.Optional;

@Repository
interface UserJpaRepository extends JpaRepository<User, EntityId> {

    @Query("SELECT u.id FROM User u WHERE u.username = :usernameVo")
    Optional<EntityId> findIdByUsername(UsernameVO usernameVo);

    Optional<User> findByUsername_Value(String username);
}
