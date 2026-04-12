package pl.kaminski.forum.thread.application.contract;

import lombok.RequiredArgsConstructor;
import pl.kaminski.forum.commons.EntityId;
import pl.kaminski.forum.commons.result.AbstractInputValidationError;
import pl.kaminski.forum.commons.result.Result;
import pl.kaminski.forum.commons.result.ResultError;
import pl.kaminski.forum.thread.domain.ThreadContentVO;
import pl.kaminski.forum.thread.domain.ThreadTitleVO;

import java.util.Map;
import java.util.UUID;

public class CreateThreadResult extends Result<CreateThreadResult.Success, CreateThreadResult.Error> {

    private CreateThreadResult(Success success) {super(success);}
    private CreateThreadResult(Error error) {super(error);}
    public static CreateThreadResult success(EntityId id) {return new CreateThreadResult(new Success(id.value()));}
    public static ValidationError.Builder errorBuilder() {return new ValidationError.Builder();}
    public static CreateThreadResult fromError(Error error) {return new CreateThreadResult(error);}
    public static CategoryNotFound categoryNotFound(UUID id) {return new CategoryNotFound(id);}


    public record Success(UUID id) { }

    public sealed interface Error extends ResultError { }

    public record CategoryNotFound(UUID id) implements Error {
        @Override
        public String getMessage() {
            return "category with given id does not exist";
        }
    }

    public static final class ValidationError extends AbstractInputValidationError<ValidationError.ViolationError, ValidationError.ViolationDetails> implements Error {

        ValidationError(Map<ViolationError, ViolationDetails> violations) {
            super(violations);
        }
        public static class Builder extends AbstractInputValidationError.Builder<ViolationError, ViolationDetails> {

            public void withCategoryEmpty() {
                withViolation(ViolationError.CATEGORY_EMPTY);
            }

            public void withTitleVoError(ThreadTitleVO.Error error) {
                var violation = switch (error) {
                    case EMPTY -> ViolationError.TITLE_EMPTY;
                    case TOO_LONG -> ViolationError.TITLE_TOO_LONG;
                    case TOO_SHORT -> ViolationError.TITLE_TOO_SHORT;
                };
                withViolation(violation);
            }

            public void withContentVoError(ThreadContentVO.Error error) {
                var violation = switch (error) {
                    case EMPTY -> ViolationError.TITLE_EMPTY;
                    case TOO_SHORT -> ViolationError.TITLE_TOO_SHORT;
                };
                withViolation(violation);
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
            CATEGORY_EMPTY(InvalidField.CATEGORY, InvalidReason.EMPTY),
            TITLE_EMPTY(InvalidField.TITLE, InvalidReason.EMPTY),
            TITLE_TOO_LONG(InvalidField.TITLE, InvalidReason.TOO_LONG),
            TITLE_TOO_SHORT(InvalidField.TITLE, InvalidReason.TOO_SHORT),
            CONTENT_EMPTY(InvalidField.CONTENT, InvalidReason.EMPTY),
            CONTENT_TOO_SHORT(InvalidField.CONTENT, InvalidReason.TOO_SHORT);

            private final InvalidField field;
            private final InvalidReason reason;
        }

        public enum InvalidField {
            TITLE,
            CONTENT,
            CATEGORY
        }
        public enum InvalidReason {
            EMPTY,
            TOO_LONG,
            TOO_SHORT,
        }

        public record ViolationDetails(InvalidField field, InvalidReason reason) { }
    }
}
