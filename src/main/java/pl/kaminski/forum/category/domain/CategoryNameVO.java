package pl.kaminski.forum.category.domain;

import jakarta.persistence.Embeddable;
import lombok.*;
import org.springframework.util.StringUtils;
import pl.kaminski.forum.commons.result.Result;
import pl.kaminski.forum.commons.result.ResultError;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryNameVO {

    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 20;

    private String name;

    public static Result<CategoryNameVO, Error> create(String value) {
        if (!StringUtils.hasText(value)) {
            return Result.error(Error.EMPTY);
        }
        if (value.length() < MIN_LENGTH) {
            return Result.error(Error.TOO_SHORT);
        }
        if (value.length() > MAX_LENGTH) {
            return Result.error(Error.TOO_LONG);
        }
        return Result.success(new CategoryNameVO(value));
    }

    @RequiredArgsConstructor
    @Getter
    public enum Error implements ResultError {
        EMPTY("Name cannot be empty"),
        TOO_SHORT("Name is too short, min length: " + MIN_LENGTH),
        TOO_LONG("Name is too long, max length: " + MAX_LENGTH);

        private final String message;
    }

}
