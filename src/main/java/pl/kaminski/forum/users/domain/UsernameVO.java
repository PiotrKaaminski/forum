package pl.kaminski.forum.users.domain;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.kaminski.forum.commons.result.ResultError;

@Embeddable
@Data
public class UsernameVO {

    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 20;

    private final String value;

    void test() {
        var error = Error.EMPTY;
        error.getMessa
    }

    @RequiredArgsConstructor
    @Getter
    public enum Error implements ResultError {
        EMPTY("username cannot be empty"),
        TOO_SHORT("username is too short, min length: " + MIN_LENGTH),
        TOO_LONG("username is too long, max length: " + MAX_LENGTH);

        private final String message;
    }

}
