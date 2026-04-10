package pl.kaminski.forum.users.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kaminski.forum.users.query.QUser;

import java.util.Optional;
import java.util.UUID;

@Repository
interface UserQueryJpaRepository extends JpaRepository<QUser, UUID> {

    Optional<QUser> findByUsername(String username);

}
