package pl.kaminski.forum.category.application;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import pl.kaminski.forum.category.application.contract.*;
import pl.kaminski.forum.category.domain.CategoryFactory;
import pl.kaminski.forum.category.domain.ICategoryRepository;
import pl.kaminski.forum.commons.AuthenticatedUser;
import pl.kaminski.forum.commons.EntityId;

@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final ICategoryRepository categoryRepository;
    private final CategoryFactory categoryFactory;

    @Override
    public CreateCategoryResult createCategory(CreateCategoryRequest request, AuthenticatedUser requestor) {
        assert request != null && requestor != null : "Request and requestor cannot be null";

        var createCategoryResult = categoryFactory.createNewCategory(request, requestor);
        if (createCategoryResult.isError()) {
            return CreateCategoryResult.fromError(createCategoryResult.getError());
        }
        var category = createCategoryResult.getSuccess();
        categoryRepository.save(category);
        return CreateCategoryResult.success(category.getId());
    }

    @Transactional
    public ModifyCategoryResult modifyCategory(EntityId id, ModifyCategoryRequest request) {
        // zabezpieczenie przed nie adminami?
        assert id != null : "Id cannot be null";
        assert request != null : "Request cannot be null";

        var categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isEmpty()) {
            return ModifyCategoryResult.categoryNotFound(id);
        }
        var category = categoryOptional.get();
        return category.modifyCategory(request);
    }
}
