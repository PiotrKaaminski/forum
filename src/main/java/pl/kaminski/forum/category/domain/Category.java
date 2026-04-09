package pl.kaminski.forum.category.domain;

import jakarta.persistence.*;
import lombok.*;
import pl.kaminski.forum.category.application.contract.CreateCategoryRequest;
import pl.kaminski.forum.category.application.contract.CreateCategoryResult;
import pl.kaminski.forum.category.application.contract.ModifyCategoryRequest;
import pl.kaminski.forum.category.application.contract.ModifyCategoryResult;
import pl.kaminski.forum.commons.DateTimeProvider;
import pl.kaminski.forum.commons.EntityId;
import pl.kaminski.forum.commons.result.Result;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "categories")
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Category {

    @Id
    @AttributeOverride(name = "value", column = @Column(name = "category_id"))
    private EntityId id;
    private CategoryNameVO name;
    @AttributeOverride(name = "value", column = @Column(name = "parent_id"))
    private EntityId parentId;
    private LocalDateTime createdAt;
    @AttributeOverride(name = "value", column = @Column(name = "created_by"))
    private EntityId createdBy;

    public static Result<Category, CreateCategoryResult.ValidationError> createFromRequest(CreateCategoryRequest request, EntityId parentId, EntityId creatorId, DateTimeProvider dateTimeProvider) {
        var validationErrorBuilder = CreateCategoryResult.errorBuilder();
        var categoryBuilder = Category.builder();

        CategoryNameVO.create(request.name()).handle(categoryBuilder::name, validationErrorBuilder::withCategoryNameVoError);

        if (validationErrorBuilder.hasViolations()) {
            return Result.error(validationErrorBuilder.build());
        }

        var category = categoryBuilder.parentId(parentId)
                .id(EntityId.newId())
                .createdAt(dateTimeProvider.currentDateTime())
                .createdBy(creatorId)
                .build();

        return Result.success(category);
    }
    public Result<Category, ModifyCategoryResult.ValidationError> modifyCategory(ModifyCategoryRequest request) {
        var validationErrorBuilder = ModifyCategoryResult.errorBuilder();

        CategoryNameVO.create(request.name()).handle(this::setName, validationErrorBuilder::withCategoryNameVoError);

        if (validationErrorBuilder.hasViolations()) {
            return Result.error(validationErrorBuilder.build());
        }

        return Result.success(this);
    }
}
