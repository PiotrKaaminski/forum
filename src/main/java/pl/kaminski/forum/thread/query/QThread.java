package pl.kaminski.forum.thread.query;

import jakarta.persistence.*;
import lombok.Data;
import pl.kaminski.forum.category.query.QCategory;
import pl.kaminski.forum.users.query.QUser;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
// todo dlaczego w CQRS wymagane są joiny na całe encje?
public class QThread {
    @Id
    @Column(name = "thread_id")
    private UUID id;
    private String title;
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private QCategory category;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private QUser createdBy;
    private LocalDateTime createdAt;
}
