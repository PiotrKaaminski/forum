package pl.kaminski.forum.category.query;

import jakarta.persistence.*;
import lombok.Data;
import pl.kaminski.forum.users.query.UserQuery;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "categories")
public class CategoryQuery {

    @Id
    @Column(name = "category_id")
    private UUID id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private CategoryQuery parent;
    private LocalDateTime createdAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private UserQuery createdBy;
}
