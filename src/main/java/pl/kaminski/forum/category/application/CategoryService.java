package pl.kaminski.forum.category.application;

import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;
import pl.kaminski.forum.category.application.contract.*;
import pl.kaminski.forum.category.domain.Category;
import pl.kaminski.forum.category.domain.CategoryNameVO;
import pl.kaminski.forum.commons.EntityId;

@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final ICategoryRepository ICategoryRepository;

    @Override
    public CreateCategoryResult createCategory(CreateCategoryRequest request) {
        Assert.notNull(request, "Request cannot be null");
        Category parentCategory = null;
        if (request.parentId() != null) {
            parentCategory = ICategoryRepository.findById(EntityId.from(request.parentId())).orElse(null);
            if (parentCategory == null) {
                return CreateCategoryResult.parentCategoryNotExists(EntityId.from(request.parentId()));
            }
        }
        var createCategoryResult = Category.createFromRequest(request, parentCategory);
        if (createCategoryResult.isError()) {
            return CreateCategoryResult.fromValidationError(createCategoryResult.getError());
        }
        var category = createCategoryResult.getSuccess();
        if (parentCategory != null) {
            var existingCategory = ICategoryRepository.findIdByNameAndParentId(category.getName(), parentCategory.getId());
            if (existingCategory.isPresent()) {
                return CreateCategoryResult.categoryNameNotUnique(existingCategory.get());
            }
        }
        ICategoryRepository.save(category);
        return CreateCategoryResult.success(category.getId().value());
    }

    public ModifyCategoryResult modifyCategory(EntityId id, ModifyCategoryRequest request) {
        Assert.notNull(request, "Request cannot be null");
        return null;
    }
    //modify
}
