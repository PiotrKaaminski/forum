package pl.kaminski.forum.users.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.kaminski.forum.AssertResult.*;

public class FirstNameVOUnitTests {

    @Test
    void given_nullValue_expect_error() {
        String value = null;
        var result = FirstNameVO.create(value);
        assertError(result, FirstNameVO.Error.EMPTY);
    }

    @Test
    void given_emptyValue_expect_error() {
        String value = "";
        var result = FirstNameVO.create(value);
        assertError(result, FirstNameVO.Error.EMPTY);
    }

    @Test
    void given_shortValue_expect_error() {
        String value = "a";
        var result = FirstNameVO.create(value);
        assertError(result, FirstNameVO.Error.TOO_SHORT);
    }

    @Test
    void given_tooLongValue_expect_error() {
        String value = "a".repeat(21);
        var result = FirstNameVO.create(value);
        assertError(result, FirstNameVO.Error.TOO_LONG);
    }

    @Test
    void given_validValue_expect_success() {
        String value = "validFirstName";
        var result = FirstNameVO.create(value);
        var success = assertIsSuccess(result);
        assertEquals(value, success.getFirstName());
    }
}
