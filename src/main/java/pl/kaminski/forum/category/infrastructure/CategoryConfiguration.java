package pl.kaminski.forum.category.infrastructure;

import org.springframework.context.annotation.Bean;
import pl.kaminski.forum.category.application.CategoryService;
import pl.kaminski.forum.category.domain.ICategoryRepository;
import pl.kaminski.forum.category.query.CategoryQueryFacade;
import pl.kaminski.forum.category.query.ICategoryQueryRepository;
import pl.kaminski.forum.commons.DateTimeProvider;
import pl.kaminski.forum.users.application.contract.IUserService;

public class CategoryConfiguration {

    private final ICategoryRepository categoryRepository;
    private final ICategoryQueryRepository categoryQueryRepository;

    CategoryConfiguration(CategoryJpaRepository categoryJpaRepository, CategoryQueryJpaRepository categoryQueryJpaRepository) {
        this.categoryRepository = new CategoryRepository(categoryJpaRepository);
        this.categoryQueryRepository = new CategoryQueryRepository(categoryQueryJpaRepository);
    }

    @Bean
    CategoryService categoryService(DateTimeProvider dateTimeProvider, IUserService userService) {
        return new CategoryService(categoryRepository, dateTimeProvider, userService);
    }

    @Bean
    CategoryQueryFacade categoryQueryFacade() {
        return new CategoryQueryFacade(categoryQueryRepository);
    }
}
