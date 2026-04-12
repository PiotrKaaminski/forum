package pl.kaminski.forum.thread.application;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import pl.kaminski.forum.commons.EntityId;
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
    @Transactional
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
    @Transactional
    public ModifyThreadResult modifyThread(EntityId id, ModifyThreadRequest request) {
        assert request != null : "Request cannot be null";
        assert id != null : "Id cannot be null";

        var threadOptional = threadRepository.findById(id);
        if (threadOptional.isEmpty()) {
            return ModifyThreadResult.threadNotFound(id);
        }
        var thread = threadOptional.get();

        if (categoryNotExistsSpecification.isSatisfiedBy(EntityId.from(request.categoryId()))) {
            return ModifyThreadResult.categoryNotFound(request.categoryId());
        }

        return thread.modifyThread(request);
    }
}
