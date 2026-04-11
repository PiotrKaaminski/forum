package pl.kaminski.forum.category.infrastructure;

import lombok.RequiredArgsConstructor;
import pl.kaminski.forum.category.domain.ICategoryRepository;
import pl.kaminski.forum.category.domain.Category;
import pl.kaminski.forum.commons.EntityId;

import java.util.Optional;

@RequiredArgsConstructor
class CategoryRepository implements ICategoryRepository {

    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public boolean existsById(EntityId categoryId) {
        return categoryJpaRepository.existsById(categoryId);
    }

    @Override
    public Optional<Category> findById(EntityId parentId) {
        return categoryJpaRepository.findById(parentId);
    }

    @Override
    public void save(Category category) {
        categoryJpaRepository.save(category);
    }

}
