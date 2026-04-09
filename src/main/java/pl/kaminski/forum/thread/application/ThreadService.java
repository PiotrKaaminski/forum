package pl.kaminski.forum.thread.application;

import lombok.RequiredArgsConstructor;
import pl.kaminski.forum.category.application.contract.ICategoryService;
import pl.kaminski.forum.commons.DateTimeProvider;
import pl.kaminski.forum.thread.application.contract.CreateThreadRequest;
import pl.kaminski.forum.thread.application.contract.CreateThreadResult;
import pl.kaminski.forum.thread.domain.IThreadRepository;
import pl.kaminski.forum.thread.application.contract.IThreadService;
import pl.kaminski.forum.thread.domain.Thread;
import pl.kaminski.forum.users.application.contract.IUserService;

@RequiredArgsConstructor
public class ThreadService implements IThreadService {

    private final IThreadRepository threadRepository;
    private final DateTimeProvider dateTimeProvider;
    private final IUserService userService;
    private final ICategoryService categoryService;

    //walidacja unikalności title? Chyba nie potrzebna
    CreateThreadResult createThread(CreateThreadRequest request) {
        assert request != null : "Request cannot be null";

        var createThreadResult = Thread.createFromRequest(request, null, null, null);
        return null;
    }
}
