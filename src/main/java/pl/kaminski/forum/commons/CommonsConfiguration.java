package pl.kaminski.forum.commons;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
public class CommonsConfiguration {

    @Bean
    DateTimeProvider dateTimeProvider() {
        return new DateTimeProvider();
    }
}
