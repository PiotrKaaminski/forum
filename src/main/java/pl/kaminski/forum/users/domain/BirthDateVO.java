package pl.kaminski.forum.users.domain;

import jakarta.persistence.Embeddable;
import lombok.*;
import org.springframework.util.StringUtils;
import pl.kaminski.forum.commons.result.Result;
import pl.kaminski.forum.commons.result.ResultError;

import java.time.LocalDate;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BirthDateVO {


    private LocalDate value;

    public static Result<BirthDateVO, Error> create(LocalDate value) {
        if (value == null) {
            return Result.error(Error.EMPTY);
        }
        if (value.isAfter(LocalDate.now())) {
            return Result.error(Error.FUTURE_BIRTHDATE);
        }
        return Result.success(new BirthDateVO(value));
    }

    @RequiredArgsConstructor
    @Getter
    public enum Error implements ResultError {
        EMPTY("birthDate cannot be empty"),
        FUTURE_BIRTHDATE("birthDate cannot be in the future");

        private final String message;
    }

}
