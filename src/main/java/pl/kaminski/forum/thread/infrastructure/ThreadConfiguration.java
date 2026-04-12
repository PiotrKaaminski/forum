package pl.kaminski.forum.thread.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.kaminski.forum.commons.DateTimeProvider;
import pl.kaminski.forum.thread.application.ThreadService;
import pl.kaminski.forum.thread.domain.CategoryNotExistsSpecification;
import pl.kaminski.forum.thread.domain.IThreadRepository;
import pl.kaminski.forum.thread.application.contract.IThreadService;
import pl.kaminski.forum.thread.domain.ThreadFactory;

public class ThreadConfiguration {

    private final IThreadRepository threadRepository;
    private final ThreadFactory threadFactory;
    private final CategoryNotExistsSpecification categoryNotExistsSpecification;

    public ThreadConfiguration(ThreadJpaRepository threadJpaRepository, DateTimeProvider dateTimeProvider, JdbcTemplate jdbcTemplate) {
        this.threadRepository = new ThreadRepository(threadJpaRepository);
        this.categoryNotExistsSpecification = categoryNotExistsSpecification(jdbcTemplate);
        this.threadFactory = new ThreadFactory(dateTimeProvider, categoryNotExistsSpecification);
    }

    @Bean
    IThreadService threadService() {
        return new ThreadService(threadRepository, threadFactory, categoryNotExistsSpecification);
    }

    private CategoryNotExistsSpecification categoryNotExistsSpecification(JdbcTemplate jdbcTemplate) {
        return categoryId -> {
            Integer count = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM categories WHERE category_id = ?",
                    Integer.class,
                    categoryId.value()
            );
            return count != null && count == 0;
        };
    }

}
