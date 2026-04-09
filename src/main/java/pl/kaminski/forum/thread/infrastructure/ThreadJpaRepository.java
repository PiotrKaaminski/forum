package pl.kaminski.forum.thread.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kaminski.forum.commons.EntityId;
import pl.kaminski.forum.thread.domain.Thread;

@Repository
public interface ThreadJpaRepository extends JpaRepository<Thread, EntityId> {
}
