package pl.kaminski.forum.users.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.kaminski.forum.AssertResult.assertError;
import static pl.kaminski.forum.AssertResult.assertIsSuccess;

public class BirthdayVOUnitTests {

    @Test
    void given_nullValue_expect_error() {
        LocalDate value = null;
        var result = BirthdateVO.create(value);
        assertError(result, BirthdateVO.Error.EMPTY);
    }

    @Test
    void given_afterCurrentDateValue_expect_error() {
        LocalDate value = LocalDate.now().plusDays(1);
        var result = BirthdateVO.create(value);
        assertError(result, BirthdateVO.Error.FUTURE_BIRTHDATE);
    }

    @Test
    void given_validValue_expect_success() {
        LocalDate value = LocalDate.now().minusDays(1);
        var result = BirthdateVO.create(value);
        var success = assertIsSuccess(result);
        assertEquals(value, success.getBirthdate());
    }
}
