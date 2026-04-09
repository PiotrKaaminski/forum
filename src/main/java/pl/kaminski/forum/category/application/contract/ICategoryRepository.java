package pl.kaminski.forum.category.application.contract;

import pl.kaminski.forum.category.domain.Category;
import pl.kaminski.forum.category.domain.CategoryNameVO;
import pl.kaminski.forum.commons.EntityId;

import java.util.Optional;

public interface ICategoryRepository {
    Optional<Category> findById(EntityId parentId);
    void save(Category category);
    Optional<EntityId> findIdByNameAndParentId(CategoryNameVO categoryNameVO, EntityId parentId);
}
