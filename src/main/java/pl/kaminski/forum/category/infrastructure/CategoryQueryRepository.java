package pl.kaminski.forum.category.infrastructure;

import lombok.RequiredArgsConstructor;
import pl.kaminski.forum.category.query.ICategoryQueryRepository;
import pl.kaminski.forum.category.query.QCategory;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
class CategoryQueryRepository implements ICategoryQueryRepository {

    private final CategoryQueryJpaRepository categoryQueryJpaRepository;

    @Override
    public Optional<QCategory> findById(UUID id) {
        return categoryQueryJpaRepository.findById(id);
    }
}
