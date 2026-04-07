package pl.kaminski.forum.category.infrastructure;

import org.springframework.context.annotation.Bean;
import pl.kaminski.forum.category.application.CategoryService;
import pl.kaminski.forum.category.application.ICategoryRepository;

public class CategoryConfiguration {

    private final ICategoryRepository ICategoryRepository;

    public CategoryConfiguration(CategoryJpaRepository categoryJpaRepository) {
        this.ICategoryRepository = new CategoryRepository(categoryJpaRepository);
    }


    @Bean
    CategoryService categoryService() {
        return new CategoryService(ICategoryRepository);
    }
}
