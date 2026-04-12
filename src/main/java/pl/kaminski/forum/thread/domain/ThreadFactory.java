package pl.kaminski.forum.thread.domain;

import lombok.RequiredArgsConstructor;
import pl.kaminski.forum.category.query.contract.ICategoryQueryFacade;
import pl.kaminski.forum.commons.AuthenticatedUser;
import pl.kaminski.forum.commons.DateTimeProvider;
import pl.kaminski.forum.commons.EntityId;
import pl.kaminski.forum.commons.result.Result;
import pl.kaminski.forum.thread.application.contract.CreateThreadRequest;
import pl.kaminski.forum.thread.application.contract.CreateThreadResult;

@RequiredArgsConstructor
public class ThreadFactory {

    private final DateTimeProvider dateTimeProvider;
    private final ICategoryQueryFacade categoryQueryFacade;

    public Result<Thread, CreateThreadResult.Error> createNewThread(CreateThreadRequest request, AuthenticatedUser requestor) {

        var validationErrorBuilder = CreateThreadResult.errorBuilder();
        var threadBuilder = Thread.builder();

        ThreadTitleVO.create(request.title()).handle(threadBuilder::title, validationErrorBuilder::withTitleVoError);
        ThreadContentVO.create(request.content()).handle(threadBuilder::content, validationErrorBuilder::withContentVoError);

        var createdBy = requestor.getId();

        if (validationErrorBuilder.hasViolations()) {
            return Result.error(validationErrorBuilder.build());
        }

        if (!categoryQueryFacade.categoryExists(request.categoryId())) {
            return Result.error(CreateThreadResult.categoryNotFound(request.categoryId()));
        }

        threadBuilder
                .categoryId(EntityId.from(request.categoryId()))
                .id(EntityId.newId())
                .createdAt(dateTimeProvider.currentDateTime())
                .createdBy(createdBy);

        return Result.success(threadBuilder.build());
    }
}
