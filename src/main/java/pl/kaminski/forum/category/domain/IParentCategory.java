package pl.kaminski.forum.category.domain;

import pl.kaminski.forum.commons.Specification;

public interface IParentCategory {
    Specification<CategoryNameVO> subcategoryWithNameExists();
}
