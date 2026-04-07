package pl.kaminski.forum.category.application.contract;

import pl.kaminski.forum.commons.EntityId;

public interface ICategoryService {
    CreateCategoryResult createCategory(CreateCategoryRequest request);
    ModifyCategoryResult modifyCategory(EntityId id, ModifyCategoryRequest request);
}
