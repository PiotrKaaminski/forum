package pl.kaminski.forum.thread.domain;

import pl.kaminski.forum.commons.EntityId;

import java.util.Optional;

public interface IThreadRepository {
    void save(Thread thread);
    Optional<Thread> findById(EntityId id);
}
