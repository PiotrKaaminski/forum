package pl.kaminski.forum.thread.query;

import lombok.RequiredArgsConstructor;
import pl.kaminski.forum.thread.query.contract.IThreadQueryFacade;

@RequiredArgsConstructor
public class ThreadQueryFacade implements IThreadQueryFacade {

    private final IThreadQueryRepository threadQueryRepository;
}
