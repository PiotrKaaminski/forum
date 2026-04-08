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
public class PasswordVO {

    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 20;

    private String password;

    static Result<PasswordVO, Error> create(String value) {
        if (!StringUtils.hasText(value)) {
            return Result.error(Error.EMPTY);
        }
        if (value.length() < MIN_LENGTH) {
            return Result.error(Error.TOO_SHORT);
        }
        if (value.length() > MAX_LENGTH) {
            return Result.error(Error.TOO_LONG);
        }
        if (StringUtils.containsWhitespace(value)) {
            return Result.error(Error.CONTAINS_WHITESPACE);
        }
        return Result.success(new PasswordVO(value));
    }

    @RequiredArgsConstructor
    @Getter
    public enum Error implements ResultError {
        EMPTY("password cannot be empty"),
        TOO_SHORT("password is too short, min length: " + MIN_LENGTH),
        TOO_LONG("password is too long, max length: " + MAX_LENGTH),
        CONTAINS_WHITESPACE("password cannot contain whitespace");

        private final String message;
    }

}
