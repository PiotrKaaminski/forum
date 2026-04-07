package pl.kaminski.forum.category.application;

import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;
import pl.kaminski.forum.category.application.contract.*;
import pl.kaminski.forum.category.domain.Category;
import pl.kaminski.forum.commons.EntityId;

@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final ICategoryRepository categoryRepository;

    @Override
    public CreateCategoryResult createCategory(CreateCategoryRequest request) {
        Assert.notNull(request, "Request cannot be null");
        EntityId parentId = null;
        if (request.parentId() != null) {
            parentId = categoryRepository.findById(EntityId.from(request.parentId())).map(Category::getId).orElse(null);
            if (parentId == null) {
                return CreateCategoryResult.parentCategoryNotExists(EntityId.from(request.parentId()));
            }
        }
        var createCategoryResult = Category.createFromRequest(request, parentId);
        if (createCategoryResult.isError()) {
            return CreateCategoryResult.fromValidationError(createCategoryResult.getError());
        }
        var category = createCategoryResult.getSuccess();
        var existingCategory = categoryRepository.findIdByNameAndParentId(category.getName(), category.getParentId());
        if (existingCategory.isPresent()) {
            return CreateCategoryResult.categoryNameNotUnique(existingCategory.get());
        }
        categoryRepository.save(category);
        return CreateCategoryResult.success(category.getId().value());
    }

    public ModifyCategoryResult modifyCategory(EntityId id, ModifyCategoryRequest request) {
        Assert.notNull(request, "Request cannot be null");
        return null;
    }
    //modify
}
