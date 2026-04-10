package pl.kaminski.forum.category.domain;

import pl.kaminski.forum.commons.EntityId;

import java.util.Optional;

public interface ICategoryRepository {
    boolean existsById(EntityId categoryId);
    Optional<Category> findById(EntityId parentId);
    void save(Category category);
    Optional<EntityId> findIdByNameAndParentId(CategoryNameVO categoryNameVO, EntityId parentId);
}
