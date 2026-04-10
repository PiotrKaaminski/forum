package pl.kaminski.forum.category.application;

import lombok.RequiredArgsConstructor;
import pl.kaminski.forum.category.application.contract.*;
import pl.kaminski.forum.category.domain.CategoryFactory;
import pl.kaminski.forum.category.domain.ICategoryRepository;
import pl.kaminski.forum.commons.EntityId;

@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final ICategoryRepository categoryRepository;
    private final CategoryFactory categoryFactory;

    @Override
    public CreateCategoryResult createCategory(CreateCategoryRequest request, String requestor) {
        assert request != null && requestor != null : "Request and requestor cannot be null";
        var createCategoryResult = categoryFactory.createNewCategory(request, requestor);
        if (createCategoryResult.isError()) {
            return CreateCategoryResult.fromError(createCategoryResult.getError());
        }
        var category = createCategoryResult.getSuccess();
        categoryRepository.save(category);
        return CreateCategoryResult.success(category.getId());
    }

    public ModifyCategoryResult modifyCategory(EntityId id, ModifyCategoryRequest request) {
        // zabezpieczenie przed nie adminami?
        assert id != null : "Id cannot be null";
        assert request != null : "Request cannot be null";

        var categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isEmpty()) {
            return ModifyCategoryResult.categoryNotFound(id);
        }
        var category = categoryOptional.get();
        var modifyCategoryResult = category.modifyCategory(request);
        if (modifyCategoryResult.isError()) {
            return ModifyCategoryResult.fromValidationError(modifyCategoryResult.getError());
        }
        var modifiedCategory = modifyCategoryResult.getSuccess();
        var existingCategoryOptional = categoryRepository.findIdByNameAndParentId(category.getName(), category.getParentId());
        if (existingCategoryOptional.isPresent()) {
            return ModifyCategoryResult.categoryNameNotUnique();
        }
        categoryRepository.save(modifiedCategory);
        return ModifyCategoryResult.success(modifiedCategory.getName().getName());
    }
}
