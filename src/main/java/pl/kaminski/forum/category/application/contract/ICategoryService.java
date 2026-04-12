package pl.kaminski.forum.category.application.contract;

import pl.kaminski.forum.users.application.contract.authentication.AuthenticatedUser;
import pl.kaminski.forum.commons.EntityId;

public interface ICategoryService {
    CreateCategoryResult createCategory(CreateCategoryRequest request, AuthenticatedUser authenticatedUser);
    ModifyCategoryResult modifyCategory(EntityId id, ModifyCategoryRequest request);
}
