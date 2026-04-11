package pl.kaminski.forum.category.application.contract;

import lombok.RequiredArgsConstructor;
import pl.kaminski.forum.category.domain.CategoryNameVO;
import pl.kaminski.forum.commons.EntityId;
import pl.kaminski.forum.commons.result.AbstractInputValidationError;
import pl.kaminski.forum.commons.result.EmptyResult;
import pl.kaminski.forum.commons.result.ResultError;

import java.util.Map;
import java.util.UUID;

public class ModifyCategoryResult extends EmptyResult<ModifyCategoryResult.Error> {

    private ModifyCategoryResult() {super();}
    private ModifyCategoryResult(Error error) {super(error);}

    public static ModifyCategoryResult success() {return new ModifyCategoryResult();}

    public static ValidationError.Builder errorBuilder() {return new ValidationError.Builder();}
    public static ModifyCategoryResult categoryNotFound(EntityId id) {return new ModifyCategoryResult(new CategoryNotFound(id.value()));}

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

            public void withCategoryNameVoError(CategoryNameVO.Error error) {
                var violation = switch (error) {
                    case EMPTY -> ViolationError.NAME_EMPTY;
                    case TOO_LONG -> ViolationError.NAME_TOO_LONG;
                    case TOO_SHORT -> ViolationError.NAME_TOO_SHORT;
                };
                withViolation(violation);
            }

            public void withNameNotUnique() {
                withViolation(ViolationError.NAME_NOT_UNIQUE);
            }

            private void withViolation(ViolationError error) {
                super.withViolation(error, new ViolationDetails(error.field, error.reason));
            }

            public ModifyCategoryResult build() {
                assert hasViolations() : "cannot build error with no violations";
                var validationError = new ValidationError(super.violations);
                return new ModifyCategoryResult(validationError);
            }
        }

        @RequiredArgsConstructor
        public enum ViolationError {
            NAME_EMPTY(InvalidField.NAME, InvalidReason.EMPTY),
            NAME_TOO_LONG(InvalidField.NAME, InvalidReason.TOO_LONG),
            NAME_TOO_SHORT(InvalidField.NAME, InvalidReason.TOO_SHORT),
            NAME_NOT_UNIQUE(InvalidField.NAME, InvalidReason.NOT_UNIQUE);

            private final InvalidField field;
            private final InvalidReason reason;
        }

        public enum InvalidField {
            NAME
        }
        public enum InvalidReason {
            EMPTY,
            TOO_LONG,
            TOO_SHORT,
            NOT_UNIQUE
        }

        public record ViolationDetails(InvalidField field, InvalidReason reason) { }
    }
}
