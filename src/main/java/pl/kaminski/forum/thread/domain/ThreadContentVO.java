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
public class ThreadContentVO {

    private static final int MIN_LENGTH = 1;

    private String content;

    public static Result<ThreadContentVO, Error> create(String value) {
        if (!StringUtils.hasText(value)) {
            return Result.error(Error.EMPTY);
        }
        if (value.length() < MIN_LENGTH) {
            return Result.error(Error.TOO_SHORT);
        }
        return Result.success(new ThreadContentVO(value));
    }

    @RequiredArgsConstructor
    @Getter
    public enum Error implements ResultError {
        EMPTY("Title cannot be empty"),
        TOO_SHORT("Title is too short, min length: " + MIN_LENGTH);

        private final String message;
    }

}
