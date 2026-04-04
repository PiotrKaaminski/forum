package pl.kaminski.forum.users.application.contract;

import lombok.RequiredArgsConstructor;
import pl.kaminski.forum.commons.EntityId;
import pl.kaminski.forum.commons.result.AbstractInputValidationError;
import pl.kaminski.forum.commons.result.Result;
import pl.kaminski.forum.commons.result.ResultError;
import pl.kaminski.forum.users.domain.*;

import java.util.Map;
import java.util.UUID;

public class RegisterUserResult extends Result<RegisterUserResult.Success, RegisterUserResult.Error> {

    private RegisterUserResult(Success success) {super(success);}

    private RegisterUserResult(Error error) {super(error);}

    public static RegisterUserResult success(UUID id) {return new RegisterUserResult(new Success(id));}
    public static ValidationError.Builder errorBuilder() {return new ValidationError.Builder();}
    public static RegisterUserResult fromValidationError(ValidationError error) {return new RegisterUserResult(error);}
    public static RegisterUserResult fromUserNotUniqueError(UsernameNotUnique error) {return new RegisterUserResult(error);}

    public record Success(UUID id) {}

    public sealed interface Error extends ResultError { }

    public record UsernameNotUnique(EntityId existingUserId) implements Error {
        @Override
        public String getMessage() {
            return "Username is not unique " + existingUserId;
        }
    }

    public static final class ValidationError extends AbstractInputValidationError<ValidationError.ViolationError, ValidationError.ViolationDetails> implements Error {

        public ValidationError(Map<ViolationError, ViolationDetails> violations) {
            super(violations);
        }

        public static class Builder extends AbstractInputValidationError.Builder<ViolationError, ViolationDetails> {

            public void withUsernameVoError(UsernameVO.Error error) {
                var violation = switch (error) {
                    case EMPTY -> ViolationError.USERNAME_EMPTY;
                    case TOO_LONG -> ViolationError.USERNAME_TOO_LONG;
                    case TOO_SHORT -> ViolationError.USERNAME_TOO_SHORT;
                };
                withViolation(violation);
            }

            public void withPasswordVoError(PasswordVO.Error error) {
                var violation = switch (error) {
                    case EMPTY -> ViolationError.PASSWORD_EMPTY;
                    case TOO_LONG -> ViolationError.PASSWORD_TOO_LONG;
                    case TOO_SHORT -> ViolationError.PASSWORD_TOO_SHORT;
                    case CONTAINS_WHITESPACE -> ViolationError.PASSWORD_CONTAINS_WHITESPACE;
                };
                withViolation(violation);
            }

            public void withFirstNameVoError(FirstNameVO.Error error) {
                var violation = switch (error) {
                    case EMPTY -> ViolationError.FIRST_NAME_EMPTY;
                    case TOO_SHORT -> ViolationError.FIRST_NAME_TOO_SHORT;
                    case TOO_LONG -> ViolationError.FIRST_NAME_TOO_LONG;
                };
                withViolation(violation);
            }

            public void withLastNameVoError(LastNameVO.Error error) {
                var violation = switch (error) {
                    case EMPTY -> ViolationError.LAST_NAME_EMPTY;
                    case TOO_SHORT -> ViolationError.LAST_NAME_TOO_SHORT;
                    case TOO_LONG -> ViolationError.LAST_NAME_TOO_LONG;
                };
                withViolation(violation);
            }

            public void withBirthDateVoError(BirthDateVO.Error error) {
                var violation = switch (error) {
                    case EMPTY -> ViolationError.BIRTHDATE_EMPTY;
                    case FUTURE_BIRTHDATE -> ViolationError.BIRTHDATE_FUTURE;
                };
                withViolation(violation);
            }

            public void withEmptyRole() {
                withViolation(ViolationError.ROLE_EMPTY);
            }

            private void withViolation(ViolationError error) {
                super.withViolation(error, new ViolationDetails(error.field, error.reason));
            }

            public ValidationError build() {
                assert hasViolations() : "cannot build error with no violations";
                return new ValidationError(super.violations);
            }
        }

        @RequiredArgsConstructor
        public enum ViolationError {
            USERNAME_EMPTY(InvalidField.USERNAME, InvalidReason.EMPTY),
            USERNAME_TOO_SHORT(InvalidField.USERNAME, InvalidReason.TOO_SHORT),
            USERNAME_TOO_LONG(InvalidField.USERNAME, InvalidReason.TOO_LONG),
            PASSWORD_EMPTY(InvalidField.PASSWORD, InvalidReason.EMPTY),
            PASSWORD_TOO_SHORT(InvalidField.PASSWORD, InvalidReason.TOO_SHORT),
            PASSWORD_TOO_LONG(InvalidField.PASSWORD, InvalidReason.TOO_LONG),
            PASSWORD_CONTAINS_WHITESPACE(InvalidField.PASSWORD, InvalidReason.CONTAINS_WHITESPACE),
            FIRST_NAME_EMPTY(InvalidField.FIRST_NAME, InvalidReason.EMPTY),
            FIRST_NAME_TOO_SHORT(InvalidField.FIRST_NAME, InvalidReason.TOO_SHORT),
            FIRST_NAME_TOO_LONG(InvalidField.FIRST_NAME, InvalidReason.TOO_LONG),
            LAST_NAME_EMPTY(InvalidField.LAST_NAME, InvalidReason.EMPTY),
            LAST_NAME_TOO_SHORT(InvalidField.LAST_NAME, InvalidReason.TOO_SHORT),
            LAST_NAME_TOO_LONG(InvalidField.LAST_NAME, InvalidReason.TOO_LONG),
            BIRTHDATE_EMPTY(InvalidField.BIRTHDATE, InvalidReason.EMPTY),
            BIRTHDATE_FUTURE(InvalidField.BIRTHDATE, InvalidReason.FUTURE_BIRTHDATE),
            ROLE_EMPTY(InvalidField.ROLE, InvalidReason.EMPTY);

            private final InvalidField field;
            private final InvalidReason reason;
        }

        public enum InvalidField{
            USERNAME,
            PASSWORD,
            FIRST_NAME,
            LAST_NAME,
            BIRTHDATE,
            ROLE
        }

        public enum InvalidReason{
            EMPTY,
            TOO_SHORT,
            TOO_LONG,
            NON_UNIQUE,
            CONTAINS_WHITESPACE,
            FUTURE_BIRTHDATE
        }

        public record ViolationDetails(InvalidField field, InvalidReason reason) {}
    }
//    public static final class  UsernameNotUnique extends AbstractInputValidationError implements Error { }
}
