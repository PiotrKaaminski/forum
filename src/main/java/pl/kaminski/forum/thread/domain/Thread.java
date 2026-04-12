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
    // todo tutaj na przykład klasa z categoryId i repo, żeby móc tam sprawdzić czy kategoria istnieje, zamiast robić to w serwisie?
    private EntityId categoryId;
    @AttributeOverride(name = "value", column = @Column(name = "created_by"))
    private EntityId createdBy;
    private LocalDateTime createdAt;

    public ModifyThreadResult modifyThread(ModifyThreadRequest request) {
        return null;
    }

}

