package pl.kaminski.forum.users.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.kaminski.forum.AssertResult.assertError;
import static pl.kaminski.forum.AssertResult.assertIsSuccess;

public class UsernameVOUnitTests {

    @Test
    void given_emptyValue_expect_error() {
        String value = null;
        var result = UsernameVO.create(value);
        assertError(result, UsernameVO.Error.EMPTY);
    }

    @Test
    void given_shortValue_expect_error() {
        String value = "a";
        var result = UsernameVO.create(value);
        assertError(result, UsernameVO.Error.TOO_SHORT);
    }

    @Test
    void given_tooLongValue_expect_error() {
        String value = "a".repeat(21);
        var result = UsernameVO.create(value);
        assertError(result, UsernameVO.Error.TOO_LONG);
    }

    @Test
    void given_validValue_expect_success() {
        String value = "validUser";
        var result = UsernameVO.create(value);
        var success = assertIsSuccess(result);
        assertEquals(value, success.getValue());
    }

    @Test
    void given_minLengthValue_expect_success() {
        String value = "abc";
        var result = UsernameVO.create(value);
        var success = assertIsSuccess(result);
        assertEquals(value, success.getValue());
    }

    @Test
    void given_maxLengthValue_expect_success() {
        String value = "a".repeat(20);
        var result = UsernameVO.create(value);
        var success = assertIsSuccess(result);
        assertEquals(value, success.getValue());
    }
}
