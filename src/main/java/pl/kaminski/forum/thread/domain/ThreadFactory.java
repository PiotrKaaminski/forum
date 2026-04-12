package pl.kaminski.forum.thread.domain;

import lombok.RequiredArgsConstructor;
import pl.kaminski.forum.users.application.contract.authentication.AuthenticatedUser;
import pl.kaminski.forum.commons.DateTimeProvider;
import pl.kaminski.forum.commons.EntityId;
import pl.kaminski.forum.commons.result.Result;
import pl.kaminski.forum.thread.application.contract.CreateThreadRequest;
import pl.kaminski.forum.thread.application.contract.CreateThreadResult;

@RequiredArgsConstructor
public class ThreadFactory {

    private final DateTimeProvider dateTimeProvider;
    private final CategoryNotExistsSpecification categoryNotExistsSpecification;

    public Result<Thread, CreateThreadResult.Error> createNewThread(CreateThreadRequest request, AuthenticatedUser requestor) {

        var validationErrorBuilder = CreateThreadResult.errorBuilder();
        var threadBuilder = Thread.builder();

        ThreadTitleVO.create(request.title()).handle(threadBuilder::title, validationErrorBuilder::withTitleVoError);
        ThreadContentVO.create(request.content()).handle(threadBuilder::content, validationErrorBuilder::withContentVoError);
        var createdBy = requestor.getId();

        if (request.categoryId() == null) {
            validationErrorBuilder.withCategoryEmpty();
        }

        if (validationErrorBuilder.hasViolations()) {
            return Result.error(validationErrorBuilder.build());
        }

        var categoryId = EntityId.from(request.categoryId());
        if (categoryNotExistsSpecification.isSatisfiedBy(categoryId)) {
            var error = CreateThreadResult.categoryNotFound(categoryId.value());
            return Result.error(error);
        }

        threadBuilder.id(EntityId.newId())
                .categoryId(categoryId)
                .createdAt(dateTimeProvider.currentDateTime())
                .createdBy(createdBy);

        return Result.success(threadBuilder.build());
    }
}
