package pl.kaminski.forum.category.domain;

import jakarta.persistence.*;
import lombok.*;
import pl.kaminski.forum.category.application.contract.CreateCategoryRequest;
import pl.kaminski.forum.category.application.contract.CreateCategoryResult;
import pl.kaminski.forum.commons.EntityId;
import pl.kaminski.forum.commons.result.Result;

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

    public static Result<Category, CreateCategoryResult.ValidationError> createFromRequest(CreateCategoryRequest request, EntityId parentId) {
        var validationErrorBuilder = CreateCategoryResult.errorBuilder();
        var categoryBuilder = Category.builder();

        CategoryNameVO.create(request.name()).handle(categoryBuilder::name, validationErrorBuilder::withCategoryNameVoError);

        if (validationErrorBuilder.hasViolations()) {
            return Result.error(validationErrorBuilder.build());
        }

        return Result.success(categoryBuilder.parentId(parentId).id(EntityId.newId()).build());
    }
    public static Result<Category, CreateCategoryResult.ValidationError> modifyCategory(CreateCategoryRequest request) {
        return null;
    }
}
