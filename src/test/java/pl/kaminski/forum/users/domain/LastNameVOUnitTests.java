package pl.kaminski.forum.users.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.kaminski.forum.AssertResult.*;

public class LastNameVOUnitTests {

    @Test
    void given_nullValue_expect_error() {
        String value = null;
        var result = LastNameVO.create(value);
        assertError(result, LastNameVO.Error.EMPTY);
    }

    @Test
    void given_emptyValue_expect_error() {
        String value = "";
        var result = LastNameVO.create(value);
        assertError(result, LastNameVO.Error.EMPTY);
    }

    @Test
    void given_shortValue_expect_error() {
        String value = "a";
        var result = LastNameVO.create(value);
        assertError(result, LastNameVO.Error.TOO_SHORT);
    }

    @Test
    void given_tooLongValue_expect_error() {
        String value = "a".repeat(21);
        var result = LastNameVO.create(value);
        assertError(result, LastNameVO.Error.TOO_LONG);
    }

    @Test
    void given_validValue_expect_success() {
        String value = "validLastName";
        var result = LastNameVO.create(value);
        var success = assertIsSuccess(result);
        assertEquals(value, success.getLastName());
    }
}
