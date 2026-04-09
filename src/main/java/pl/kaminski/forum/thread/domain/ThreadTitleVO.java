package pl.kaminski.forum.thread.domain;

import jakarta.persistence.Embeddable;
import lombok.*;
import org.springframework.util.StringUtils;
import pl.kaminski.forum.commons.result.Result;
import pl.kaminski.forum.commons.result.ResultError;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThreadTitleVO {

    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 20;

    private String title;

    public static Result<ThreadTitleVO, Error> create(String value) {
        if (!StringUtils.hasText(value)) {
            return Result.error(Error.EMPTY);
        }
        if (value.length() < MIN_LENGTH) {
            return Result.error(Error.TOO_SHORT);
        }
        if (value.length() > MAX_LENGTH) {
            return Result.error(Error.TOO_LONG);
        }
        return Result.success(new ThreadTitleVO(value));
    }

    @RequiredArgsConstructor
    @Getter
    public enum Error implements ResultError {
        EMPTY("Title cannot be empty"),
        TOO_SHORT("Title is too short, min length: " + MIN_LENGTH),
        TOO_LONG("Title is too long, max length: " + MAX_LENGTH);

        private final String message;
    }

}
