package pl.kaminski.forum.category.application;

import lombok.RequiredArgsConstructor;
import pl.kaminski.forum.category.application.contract.*;
import pl.kaminski.forum.category.domain.Category;
import pl.kaminski.forum.category.domain.ICategoryRepository;
import pl.kaminski.forum.commons.DateTimeProvider;
import pl.kaminski.forum.commons.EntityId;
import pl.kaminski.forum.users.application.contract.IUserService;

@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final ICategoryRepository categoryRepository;
    private final DateTimeProvider dateTimeProvider;
    private final IUserService userService;

    @Override
    public CreateCategoryResult createCategory(CreateCategoryRequest request, String username) {
        assert request != null : "Request cannot be null";

        EntityId parentId = null;
        if (request.parentId() != null) {
            parentId = categoryRepository.findById(EntityId.from(request.parentId())).map(Category::getId).orElse(null);
            if (parentId == null) {
                return CreateCategoryResult.parentCategoryNotExists(EntityId.from(request.parentId()));
            }
        }
        var createdBy = userService.getByUsername(username).orElseThrow(() -> new RuntimeException("User with username " + username + " not found"));
        var createCategoryResult = Category.createFromRequest(request, parentId, createdBy, dateTimeProvider);
        if (createCategoryResult.isError()) {
            return CreateCategoryResult.fromValidationError(createCategoryResult.getError());
        }
        var category = createCategoryResult.getSuccess();
        var existingCategoryOptional = categoryRepository.findIdByNameAndParentId(category.getName(), category.getParentId());
        if (existingCategoryOptional.isPresent()) {
            return CreateCategoryResult.categoryNameNotUnique(existingCategoryOptional.get());
        }
        categoryRepository.save(category);
        return CreateCategoryResult.success(category.getId().value());
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
            return ModifyCategoryResult.categoryNameNotUnique(existingCategoryOptional.get());
        }
        categoryRepository.save(modifiedCategory);
        return ModifyCategoryResult.success(modifiedCategory.getName().getName());
    }
}
