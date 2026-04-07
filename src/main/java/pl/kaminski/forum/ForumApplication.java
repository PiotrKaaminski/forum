package pl.kaminski.forum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import pl.kaminski.forum.category.infrastructure.CategoryConfiguration;
import pl.kaminski.forum.commons.CommonsConfiguration;
import pl.kaminski.forum.users.infrastructure.UserConfiguration;

@SpringBootApplication
@Import({UserConfiguration.class, CommonsConfiguration.class, CategoryConfiguration.class})
public class ForumApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForumApplication.class, args);
    }

}
