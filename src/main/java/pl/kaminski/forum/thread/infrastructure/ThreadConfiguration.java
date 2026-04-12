package pl.kaminski.forum.thread.infrastructure;

import org.springframework.context.annotation.Bean;
import pl.kaminski.forum.category.query.CategoryQueryFacade;
import pl.kaminski.forum.category.query.contract.ICategoryQueryFacade;
import pl.kaminski.forum.commons.DateTimeProvider;
import pl.kaminski.forum.thread.application.ThreadService;
import pl.kaminski.forum.thread.domain.IThreadRepository;
import pl.kaminski.forum.thread.application.contract.IThreadService;
import pl.kaminski.forum.thread.domain.ThreadFactory;

public class ThreadConfiguration {

    private final IThreadRepository threadRepository;
    private final ThreadFactory threadFactory;

    public ThreadConfiguration(ThreadJpaRepository threadJpaRepository, DateTimeProvider dateTimeProvider, ICategoryQueryFacade categoryQueryFacade) {
        this.threadRepository = new ThreadRepository(threadJpaRepository);
        this.threadFactory = new ThreadFactory(dateTimeProvider, categoryQueryFacade);
    }

    @Bean
    IThreadService threadService(CategoryQueryFacade categoryQueryFacade) {
        return new ThreadService(threadRepository, threadFactory, categoryQueryFacade);
    }
}
