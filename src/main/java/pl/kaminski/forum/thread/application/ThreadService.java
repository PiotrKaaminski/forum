package pl.kaminski.forum.thread.application;

import lombok.RequiredArgsConstructor;
import pl.kaminski.forum.thread.domain.CategoryNotExistsSpecification;
import pl.kaminski.forum.users.application.contract.authentication.AuthenticatedUser;
import pl.kaminski.forum.thread.application.contract.*;
import pl.kaminski.forum.thread.domain.IThreadRepository;
import pl.kaminski.forum.thread.domain.ThreadFactory;

@RequiredArgsConstructor
public class ThreadService implements IThreadService {

    private final IThreadRepository threadRepository;
    private final ThreadFactory threadFactory;
    private final CategoryNotExistsSpecification categoryNotExistsSpecification;


    @Override
    public CreateThreadResult createThread(CreateThreadRequest request, AuthenticatedUser requestor) {
        assert request != null && requestor != null : "Request and requestor cannot be null";

        var createThreadResult = threadFactory.createNewThread(request, requestor);
        if (createThreadResult.isError()) {
            return CreateThreadResult.fromError(createThreadResult.getError());
        }
        var thread = createThreadResult.getSuccess();
        threadRepository.save(thread);
        return CreateThreadResult.success(thread.getId());
    }

    @Override
    public ModifyThreadResult modifyThread(ModifyThreadRequest request) {
        assert request != null : "Request cannot be null";

//        categoryExistsSpecification.isSatisfiedBy(categoryId, threadBuilder::categoryId, validationErrorBuilder::withCategoryNotFound);

        return null;
    }
}
