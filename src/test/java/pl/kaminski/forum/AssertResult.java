package pl.kaminski.forum;

import pl.kaminski.forum.commons.result.Result;
import pl.kaminski.forum.commons.result.ResultError;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AssertResult {

    public static <S, E extends ResultError> E assertIsError(Result<S, E> result) {
        assertTrue(result.isError());
        return result.getError();
    }

    public static <S, E extends ResultError> E assertError(Result<S, E> result, E expectedError) {
        assertTrue(result.isError());
        assertEquals(expectedError, result.getError());
        return result.getError();
    }

    public static <S, E extends ResultError> S assertIsSuccess(Result<S, E> result) {
        assertTrue(result.isSuccess());
        return result.getSuccess();
    }


}
