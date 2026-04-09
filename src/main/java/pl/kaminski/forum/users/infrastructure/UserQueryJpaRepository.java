package pl.kaminski.forum.users.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kaminski.forum.users.query.User;

import java.util.Optional;
import java.util.UUID;

@Repository
interface UserQueryJpaRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);

}
