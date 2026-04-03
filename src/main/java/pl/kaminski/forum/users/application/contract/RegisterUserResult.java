package pl.kaminski.forum.users.application.contract;

import lombok.RequiredArgsConstructor;
import pl.kaminski.forum.commons.result.AbstractInputValidationError;
import pl.kaminski.forum.commons.result.Result;
import pl.kaminski.forum.commons.result.ResultError;
import pl.kaminski.forum.users.domain.PasswordVO;
import pl.kaminski.forum.users.domain.UsernameVO;

import java.util.Map;
import java.util.UUID;

public class RegisterUserResult extends Result<RegisterUserResult.Success, RegisterUserResult.Error> {

    private RegisterUserResult(Success success) {super(success);}

    private RegisterUserResult(Error error) {super(error);}

    public static RegisterUserResult success(UUID id) {return new RegisterUserResult(new Success(id));}
    public static ValidationError.Builder errorBuilder() {return new ValidationError.Builder();}
    public static RegisterUserResult fromValidationError(ValidationError error) {return new RegisterUserResult(error);}

    public record Success(UUID id) {}

    public sealed interface Error extends ResultError { }

    public static final class ValidationError extends AbstractInputValidationError<ValidationError.ViolationError, ValidationError.ViolationDetails> implements Error {

        public ValidationError(Map<ViolationError, ViolationDetails> violations) {
            super(violations);
        }

        public static class Builder extends AbstractInputValidationError.Builder<ValidationError.ViolationError, ValidationError.ViolationDetails> {

            public Builder withUsernameVoResult(Result<?, UsernameVO.Error> usernameVoResult) {
                if (usernameVoResult.isSuccess()) return this;
                var error = usernameVoResult.getError();
                var violation = switch (error) {
                    case EMPTY -> ViolationError.USERNAME_EMPTY;
                    case TOO_LONG -> ViolationError.USERNAME_TOO_LONG;
                    case TOO_SHORT -> ViolationError.USERNAME_TOO_SHORT;
                };
                withViolation(violation);
                return this;
            }

            public Builder withPasswordVoResult(Result<?, PasswordVO.Error> usernameVoResult) {
                if (usernameVoResult.isSuccess()) return this;
                var error = usernameVoResult.getError();
                var violation = switch (error) {
                    case EMPTY -> ViolationError.PASSWORD_EMPTY;
                    case TOO_LONG -> ViolationError.PASSWORD_TOO_LONG;
                    case TOO_SHORT -> ViolationError.PASSWORD_TOO_SHORT;
                    case CONTAINS_WHITESPACE -> ViolationError.PASSWORD_CONTAINS_WHITESPACE;
                };
                withViolation(violation);
                return this;
            }

            public Builder withEmptyRole() {
                withViolation(ViolationError.ROLE_EMPTY);
                return this;
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
            EMAIL_EMPTY(InvalidField.EMAIL, InvalidReason.EMPTY),
            EMAIL_INVALID(InvalidField.EMAIL, InvalidReason.INVALID),
            ROLE_EMPTY(InvalidField.ROLE, InvalidReason.EMPTY);

            private final InvalidField field;
            private final InvalidReason reason;
        }

        public enum InvalidField{
            USERNAME,
            PASSWORD,
            EMAIL,
            ROLE
        }

        public enum InvalidReason{
            EMPTY,
            INVALID,
            TOO_SHORT,
            TOO_LONG,
            NON_UNIQUE,
            CONTAINS_WHITESPACE
        }

        public record ViolationDetails(InvalidField field, InvalidReason reason) {}
    }
}
