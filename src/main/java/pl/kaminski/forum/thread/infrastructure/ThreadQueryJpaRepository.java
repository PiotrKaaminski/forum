package pl.kaminski.forum.thread.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kaminski.forum.thread.query.QThread;

import java.util.UUID;

@Repository
public interface ThreadQueryJpaRepository extends JpaRepository<QThread, UUID> {
}
