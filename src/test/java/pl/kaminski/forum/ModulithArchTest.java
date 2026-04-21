package pl.kaminski.forum;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.core.DependencyType;
import org.springframework.modulith.docs.Documenter;

import java.util.Collections;

public class ModulithArchTest {

    @Test
    void testApplicationModules() {
        var modules = ApplicationModules.of(ForumApplication.class);
        modules.forEach(System.out::println);
        modules.verify();
        new Documenter(modules).writeDocumentation();
    }

}
