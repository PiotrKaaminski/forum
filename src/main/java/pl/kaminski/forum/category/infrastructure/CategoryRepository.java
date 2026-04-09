package pl.kaminski.forum.category.infrastructure;

import lombok.RequiredArgsConstructor;
import pl.kaminski.forum.category.application.contract.ICategoryRepository;
import pl.kaminski.forum.category.domain.Category;
import pl.kaminski.forum.category.domain.CategoryNameVO;
import pl.kaminski.forum.commons.EntityId;

import java.util.Optional;

@RequiredArgsConstructor
public class CategoryRepository implements ICategoryRepository {

    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public Optional<Category> findById(EntityId parentId) {
        return categoryJpaRepository.findById(parentId);
    }

    @Override
    public void save(Category category) {
        categoryJpaRepository.save(category);
    }

    // dla name + parent=null nie znajduje w bazie rekordu, stąd te rozbicie.
    @Override
    public Optional<EntityId> findIdByNameAndParentId(CategoryNameVO categoryNameVO, EntityId parentId) {
        if (parentId == null) {
            return categoryJpaRepository.findIdByNameAndNullParent(categoryNameVO);
        }
        return categoryJpaRepository.findIdByNameAndParentId(categoryNameVO, parentId);
    }
}
