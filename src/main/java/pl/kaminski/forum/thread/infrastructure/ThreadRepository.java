package pl.kaminski.forum.thread.infrastructure;

import lombok.RequiredArgsConstructor;
import pl.kaminski.forum.commons.EntityId;
import pl.kaminski.forum.thread.domain.IThreadRepository;
import pl.kaminski.forum.thread.domain.Thread;

import java.util.Optional;

@RequiredArgsConstructor
public class ThreadRepository implements IThreadRepository {

    private final ThreadJpaRepository threadJpaRepository;

    public void save(Thread thread) {
        threadJpaRepository.save(thread);
    }

    public Optional<Thread> findById(EntityId threadId) {
        return threadJpaRepository.findById(threadId);
    }
}
