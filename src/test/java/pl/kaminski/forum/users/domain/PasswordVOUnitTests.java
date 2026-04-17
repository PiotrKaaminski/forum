package pl.kaminski.forum.users.domain;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.kaminski.forum.AssertResult.*;


public class PasswordVOUnitTests {


    @Test
    void given_validValue_expect_success() {
        var passwordEncoder = Mockito.mock(PasswordEncoder.class);
        var encodedPassword = "encodedPassword";
        String value = "testPassword";
        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn(encodedPassword);
        var result = PasswordVO.create(value, passwordEncoder);
        var success = assertIsSuccess(result);

        assertEquals(encodedPassword, success.getPassword());
        Mockito.verify(passwordEncoder, Mockito.times(1)).encode(value);

    }

    @Test
    void given_invalidPassword_expect_error() {
        var passwordEncoder = Mockito.mock(PasswordEncoder.class);
        String value = "  ";
        var result = PasswordVO.create(value, passwordEncoder);
        assertError(result, PasswordVO.Error.EMPTY);

        Mockito.verify(passwordEncoder, Mockito.never()).encode(Mockito.anyString());
    }
}
