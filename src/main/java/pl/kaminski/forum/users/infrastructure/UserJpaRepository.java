package pl.kaminski.forum.users.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.kaminski.forum.commons.EntityId;
import pl.kaminski.forum.users.domain.User;

@Repository
public interface UserJpaRepository extends JpaRepository<User, EntityId> {

    @Query("SELECT u FROM User u WHERE u.username.value = :username")
    Boolean existsByUsername(String username);
}
