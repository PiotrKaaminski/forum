package pl.kaminski.forum.category.infrastructure;

import lombok.RequiredArgsConstructor;
import pl.kaminski.forum.category.domain.CategoryNameVO;
import pl.kaminski.forum.category.domain.IParentCategory;
import pl.kaminski.forum.commons.Specification;

@RequiredArgsConstructor
class RootParentCategory implements IParentCategory {

    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public Specification<CategoryNameVO> subcategoryWithNameExists() {
        return name -> categoryJpaRepository.existsByNameAndParentCategory(name, null);
    }
}
