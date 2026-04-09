package pl.kaminski.forum.category.query;

import jakarta.persistence.*;
import lombok.Data;
import pl.kaminski.forum.users.query.User;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "CategoryQuery")
@Data
@Table(name = "categories")
public class Category {

    @Id
    @Column(name = "category_id")
    private UUID id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;
    private LocalDateTime createdAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;
}
