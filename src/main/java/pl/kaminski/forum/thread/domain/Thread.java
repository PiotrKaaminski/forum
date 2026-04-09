package pl.kaminski.forum.thread.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.kaminski.forum.commons.DateTimeProvider;
import pl.kaminski.forum.commons.EntityId;
import pl.kaminski.forum.commons.result.Result;
import pl.kaminski.forum.thread.application.contract.CreateThreadRequest;
import pl.kaminski.forum.thread.application.contract.CreateThreadResult;

import java.time.LocalDateTime;

@Entity
@Table(name = "threads")
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Thread {
    @Id
    @AttributeOverride(name = "value", column = @Column(name = "thread_id"))
    private EntityId id;
    private ThreadTitleVO title;
    private ThreadContentVO content;
    @AttributeOverride(name = "value", column = @Column(name = "category_id"))
    private EntityId categoryId;
    @AttributeOverride(name = "value", column = @Column(name = "created_by"))
    private EntityId createdBy;
    private LocalDateTime createdAt;

    public static Result<Thread, CreateThreadResult.ValidationError> createFromRequest(CreateThreadRequest request, EntityId categoryId, EntityId userId, DateTimeProvider dateTimeProvider) {
        var validationErrorBuilder = CreateThreadResult.errorBuilder();
        var threadBuilder = Thread.builder();

        ThreadTitleVO.create(request.title()).handle(threadBuilder::title, validationErrorBuilder::withTitleVoError);
        ThreadContentVO.create(request.content()).handle(threadBuilder::content, validationErrorBuilder::withContentVoError);

        if (validationErrorBuilder.hasViolations()) {
            return Result.error(validationErrorBuilder.build());
        }
        return Result.success(threadBuilder.categoryId(categoryId).id(EntityId.newId()).createdAt(dateTimeProvider.currentDateTime()).createdBy(userId).build());
    }
}

