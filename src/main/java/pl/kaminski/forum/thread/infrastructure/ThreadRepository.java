package pl.kaminski.forum.thread.infrastructure;

import lombok.RequiredArgsConstructor;
import pl.kaminski.forum.thread.domain.IThreadRepository;
import pl.kaminski.forum.thread.domain.Thread;

@RequiredArgsConstructor
public class ThreadRepository implements IThreadRepository {

    private final ThreadJpaRepository threadJpaRepository;

    public void save(Thread thread) {
        threadJpaRepository.save(thread);
    }
}
