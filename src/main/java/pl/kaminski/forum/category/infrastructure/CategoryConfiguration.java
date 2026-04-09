package pl.kaminski.forum.category.infrastructure;

import org.springframework.context.annotation.Bean;
import pl.kaminski.forum.category.application.CategoryService;
import pl.kaminski.forum.category.domain.ICategoryRepository;
import pl.kaminski.forum.commons.DateTimeProvider;
import pl.kaminski.forum.users.application.contract.IUserService;

public class CategoryConfiguration {

    private final ICategoryRepository ICategoryRepository;

    public CategoryConfiguration(CategoryJpaRepository categoryJpaRepository) {
        this.ICategoryRepository = new CategoryRepository(categoryJpaRepository);
    }

    @Bean
    CategoryService categoryService(DateTimeProvider dateTimeProvider, IUserService userService) {
        return new CategoryService(ICategoryRepository, dateTimeProvider, userService);
    }
}
