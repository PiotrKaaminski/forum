package pl.kaminski.forum.users.domain;

import jakarta.persistence.Embeddable;
import lombok.*;
import org.springframework.util.StringUtils;
import pl.kaminski.forum.commons.result.Result;
import pl.kaminski.forum.commons.result.ResultError;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FirstNameVO {

    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 20;

    private String value;

    static Result<FirstNameVO, Error> create(String value) {
        if (!StringUtils.hasText(value)) {
            return Result.error(Error.EMPTY);
        }
        if (value.length() < MIN_LENGTH) {
            return Result.error(Error.TOO_SHORT);
        }
        if (value.length() > MAX_LENGTH) {
            return Result.error(Error.TOO_LONG);
        }
        return Result.success(new FirstNameVO(value));
    }

    @RequiredArgsConstructor
    @Getter
    public enum Error implements ResultError {
        EMPTY("firstName cannot be empty"),
        TOO_SHORT("firstName is too short, min length: " + MIN_LENGTH),
        TOO_LONG("firstName is too long, max length: " + MAX_LENGTH);

        private final String message;
    }

}
