package pl.kaminski.forum.category.infrastructure;

import lombok.RequiredArgsConstructor;
import pl.kaminski.forum.category.query.ICategoryQueryRepository;

@RequiredArgsConstructor
class CategoryQueryRepository implements ICategoryQueryRepository {

    private final CategoryQueryJpaRepository categoryQueryJpaRepository;
}
