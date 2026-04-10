package pl.kaminski.forum.category.query;

import jakarta.persistence.*;
import lombok.Data;
import pl.kaminski.forum.users.query.QUser;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "categories")
public class QCategory {

    @Id
    @Column(name = "category_id")
    private UUID id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private QCategory parent;
    private LocalDateTime createdAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private QUser createdBy;
}
