package pl.kaminski.forum.category.infrastructure;

import org.springframework.context.annotation.Bean;
import pl.kaminski.forum.category.application.CategoryService;
import pl.kaminski.forum.category.domain.CategoryFactory;
import pl.kaminski.forum.category.domain.ICategoryRepository;
import pl.kaminski.forum.category.domain.IParentCategory;
import pl.kaminski.forum.category.query.CategoryQueryFacade;
import pl.kaminski.forum.category.query.ICategoryQueryRepository;
import pl.kaminski.forum.commons.DateTimeProvider;
import pl.kaminski.forum.commons.EntityId;
import pl.kaminski.forum.users.application.contract.IUserService;

import java.util.UUID;

public class CategoryConfiguration {

    private final ICategoryRepository categoryRepository;
    private final ICategoryQueryRepository categoryQueryRepository;
    private final CategoryFactory categoryFactory;

    CategoryConfiguration(CategoryJpaRepository categoryJpaRepository, CategoryQueryJpaRepository categoryQueryJpaRepository, DateTimeProvider dateTimeProvider, IUserService userService) {
        this.categoryRepository = new CategoryRepository(categoryJpaRepository);
        this.categoryQueryRepository = new CategoryQueryRepository(categoryQueryJpaRepository);
        this.categoryFactory = new CategoryFactory(categoryRepository, dateTimeProvider, userService, this::parentCategoryFactory);
    }

    @Bean
    CategoryService categoryService() {
        return new CategoryService(categoryRepository, categoryFactory);
    }

    @Bean
    CategoryQueryFacade categoryQueryFacade() {
        return new CategoryQueryFacade(categoryQueryRepository);
    }

    private IParentCategory parentCategoryFactory(UUID parentId) {
        return parentId == null ? new RootParentCategory() : new ParentCategory(EntityId.from(parentId));
    }
}
