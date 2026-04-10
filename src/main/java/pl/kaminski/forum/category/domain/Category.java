package pl.kaminski.forum.category.domain;

import jakarta.persistence.*;
import lombok.*;
import pl.kaminski.forum.category.application.contract.ModifyCategoryRequest;
import pl.kaminski.forum.category.application.contract.ModifyCategoryResult;
import pl.kaminski.forum.commons.EntityId;
import pl.kaminski.forum.commons.result.Result;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "categories")
@Builder(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Category {

    @Id
    @AttributeOverride(name = "value", column = @Column(name = "category_id"))
    private EntityId id;
    private CategoryNameVO name;
    private IParentCategory parentCategory;
    private LocalDateTime createdAt;
    @AttributeOverride(name = "value", column = @Column(name = "created_by"))
    private EntityId createdBy;

    public Result<Category, ModifyCategoryResult.ValidationError> modifyCategory(ModifyCategoryRequest request) {
        var validationErrorBuilder = ModifyCategoryResult.errorBuilder();

        CategoryNameVO.create(request.name()).handle(this::setName, validationErrorBuilder::withCategoryNameVoError);

        if (validationErrorBuilder.hasViolations()) {
            return Result.error(validationErrorBuilder.build());
        }

        return Result.success(this);
    }
}
