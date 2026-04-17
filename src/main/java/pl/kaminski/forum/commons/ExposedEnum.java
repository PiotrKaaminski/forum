package pl.kaminski.forum.commons;

import org.springframework.modulith.NamedInterface;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@NamedInterface(name = "enums", propagate = false)
public @interface ExposedEnum {
}
