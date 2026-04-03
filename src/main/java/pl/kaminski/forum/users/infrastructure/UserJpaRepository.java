package pl.kaminski.forum.users.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kaminski.forum.commons.EntityId;
import pl.kaminski.forum.users.domain.User;

@Repository
interface UserJpaRepository extends JpaRepository<User, EntityId> {
}
