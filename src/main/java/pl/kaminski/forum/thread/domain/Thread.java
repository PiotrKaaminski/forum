package pl.kaminski.forum.thread.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.StringUtils;
import pl.kaminski.forum.commons.EntityId;
import pl.kaminski.forum.commons.Modifiers;
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

    public ModifyThreadResult modifyThread(ModifyThreadRequest request) {
        var validationErrorBuilder = ModifyThreadResult.errorBuilder();
        var modifiers = new Modifiers();

        if (StringUtils.hasText(request.title())) {
            ThreadTitleVO.create(request.title()).handle(title -> modifiers.add(title, this::setTitle), validationErrorBuilder::withTitleVoError);
        }
        if (StringUtils.hasText(request.content())) {
            ThreadContentVO.create(request.content()).handle(content -> modifiers.add(content, this::setContent),validationErrorBuilder::withContentVoError);
        }
        if (request.categoryId() != null) {
            modifiers.add(EntityId.from(request.categoryId()), this::setCategoryId);
        }
        if (validationErrorBuilder.hasViolations()) {
            return validationErrorBuilder.build();
        }
        modifiers.modifyAll();
        return ModifyThreadResult.success();
    }


}

