package pl.kaminski.forum.category.domain;

import lombok.RequiredArgsConstructor;
import pl.kaminski.forum.category.application.contract.CreateCategoryRequest;
import pl.kaminski.forum.category.application.contract.CreateCategoryResult;
import pl.kaminski.forum.commons.DateTimeProvider;
import pl.kaminski.forum.commons.EntityId;
import pl.kaminski.forum.commons.Specification;
import pl.kaminski.forum.commons.result.Result;
import pl.kaminski.forum.users.application.contract.IUserService;

import java.util.UUID;
import java.util.function.Function;

@RequiredArgsConstructor
public class CategoryFactory {

    private final ICategoryRepository categoryRepository;
    private final DateTimeProvider dateTimeProvider;
    private final IUserService userService;
    private final Function<UUID, IParentCategory> parentCategoryProvider;

    public Result<Category, CreateCategoryResult.Error> createNewCategory(CreateCategoryRequest request, String requestor) {
        assert request != null && requestor != null : "Request and requestor cannot be null";

        var createdBy = userService.getIdByUsername(requestor).orElseThrow(() -> new RuntimeException("User with username " + requestor + " not found"));
        if (parentDoesNotExist().isSatisfiedBy(request.parentId())) {
            return Result.error(CreateCategoryResult.parentCategoryNotExists(request.parentId()));
        }
        var parentCategory = parentCategoryProvider.apply(request.parentId());
        var categoryBuilder = Category.builder();
        var validationErrorBuilder = CreateCategoryResult.errorBuilder();

        CategoryNameVO.create(request.name()).handle(
                name -> parentCategory.subcategoryWithNameExists().isSatisfiedBy(name, validationErrorBuilder::withNameNotUnique, categoryBuilder::name),
                validationErrorBuilder::withCategoryNameVoError
        );

        if (validationErrorBuilder.hasViolations()) {
            return Result.error(validationErrorBuilder.build());
        }

        var category = categoryBuilder.id(EntityId.newId())
                .parentCategory(parentCategory)
                .createdBy(createdBy)
                .createdAt(dateTimeProvider.currentDateTime())
                .build();
        return Result.success(category);
    }

    private Specification<UUID> parentDoesNotExist() {
        return parentId -> {
            if (parentId == null) return false;
            return !categoryRepository.existsById(EntityId.from(parentId));
        };
    }

}
