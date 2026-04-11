package pl.kaminski.forum.category.infrastructure;

import lombok.Getter;
import pl.kaminski.forum.category.domain.CategoryNameVO;
import pl.kaminski.forum.category.domain.IParentCategory;
import pl.kaminski.forum.commons.EntityId;
import pl.kaminski.forum.commons.Specification;

import java.util.UUID;

class ParentCategory implements IParentCategory {

    @Getter
    private final EntityId categoryId;
    private final CategoryJpaRepository categoryJpaRepository;

    ParentCategory(UUID categoryId, CategoryJpaRepository categoryJpaRepository) {
        this.categoryId = EntityId.from(categoryId);
        this.categoryJpaRepository = categoryJpaRepository;
    }

    @Override
    public Specification<CategoryNameVO> subcategoryWithNameExists() {
        return name -> categoryJpaRepository.existsByNameAndParentCategory(name, this);
    }

    EntityId getId() {
        return categoryId;
    }
}
