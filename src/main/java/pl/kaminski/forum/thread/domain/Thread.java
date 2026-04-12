package pl.kaminski.forum.thread.domain;

import jakarta.persistence.*;
import lombok.*;
import pl.kaminski.forum.commons.EntityId;
import pl.kaminski.forum.thread.application.contract.ModifyThreadRequest;
import pl.kaminski.forum.thread.application.contract.ModifyThreadResult;

import java.time.LocalDateTime;

@Entity
@Table(name = "threads")
@Builder(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
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

    // todo do zmiany, żeby zmieniać stan dopiero po walidacji
    public ModifyThreadResult modifyThread(ModifyThreadRequest request) {
        var validationErrorBuilder = ModifyThreadResult.errorBuilder();

        ThreadTitleVO.create(request.title()).handle(this::setTitle, validationErrorBuilder::withTitleVoError);
        ThreadContentVO.create(request.content()).handle(this::setContent, validationErrorBuilder::withContentVoError);

        if (request.categoryId() == null) {
            validationErrorBuilder.withCategoryEmpty();
        }
        this.categoryId = EntityId.from(request.categoryId());

        if (validationErrorBuilder.hasViolations()) {
            return validationErrorBuilder.build();
        }

        return ModifyThreadResult.success();
    }

}

