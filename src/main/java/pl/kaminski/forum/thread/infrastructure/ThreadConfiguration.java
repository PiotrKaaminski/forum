package pl.kaminski.forum.thread.infrastructure;

import org.springframework.context.annotation.Bean;
import pl.kaminski.forum.category.application.contract.ICategoryService;
import pl.kaminski.forum.commons.DateTimeProvider;
import pl.kaminski.forum.thread.application.ThreadService;
import pl.kaminski.forum.thread.application.contract.IThreadRepository;
import pl.kaminski.forum.thread.application.contract.IThreadService;
import pl.kaminski.forum.users.application.contract.IUserService;

public class ThreadConfiguration {

    private final IThreadRepository threadRepository;

    public ThreadConfiguration(ThreadJpaRepository threadJpaRepository) {
        this.threadRepository = new ThreadRepository();
    }

    @Bean
    IThreadService threadService(DateTimeProvider dateTimeProvider, IUserService userService, ICategoryService categoryService) {
        return new ThreadService(threadRepository, dateTimeProvider, userService, categoryService);
    }
}
