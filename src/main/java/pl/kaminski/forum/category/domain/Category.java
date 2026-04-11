package pl.kaminski.forum.category.domain;

import jakarta.persistence.*;
import lombok.*;
import pl.kaminski.forum.category.application.contract.ModifyCategoryRequest;
import pl.kaminski.forum.category.application.contract.ModifyCategoryResult;
import pl.kaminski.forum.commons.EntityId;

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
    @SuppressWarnings("JpaAttributeTypeInspection")
    @Column(name = "parent_id")
    private IParentCategory parentCategory;
    private LocalDateTime createdAt;
    @AttributeOverride(name = "value", column = @Column(name = "created_by"))
    private EntityId createdBy;

    public ModifyCategoryResult modifyCategory(ModifyCategoryRequest request) {
        var validationErrorBuilder = ModifyCategoryResult.errorBuilder();

        CategoryNameVO.create(request.name()).handle(
                name -> parentCategory.subcategoryWithNameExists().isSatisfiedBy(name, validationErrorBuilder::withNameNotUnique, this::setName),
                validationErrorBuilder::withCategoryNameVoError
        );

        if (validationErrorBuilder.hasViolations()) {
            return validationErrorBuilder.build();
        }

        return ModifyCategoryResult.success();
    }
}
