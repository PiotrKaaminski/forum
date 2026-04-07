package pl.kaminski.forum.category.application.contract;

import lombok.RequiredArgsConstructor;
import pl.kaminski.forum.category.domain.CategoryNameVO;
import pl.kaminski.forum.commons.EntityId;
import pl.kaminski.forum.commons.result.AbstractInputValidationError;
import pl.kaminski.forum.commons.result.Result;
import pl.kaminski.forum.commons.result.ResultError;

import java.util.Map;
import java.util.UUID;

public class CreateCategoryResult extends Result<CreateCategoryResult.Success, CreateCategoryResult.Error> {

    private CreateCategoryResult(Success success) {super(success);}
    private CreateCategoryResult(Error error) {super(error);}
    public static CreateCategoryResult success(UUID id) {return new CreateCategoryResult(new Success(id));}
    public static ValidationError.Builder errorBuilder() {return new ValidationError.Builder();}
    public static CreateCategoryResult fromValidationError(ValidationError error) {return new CreateCategoryResult(error);}
    public static CreateCategoryResult parentCategoryNotExists(EntityId id) {return new CreateCategoryResult(new ParentCategoryNotExists(id.value()));}
    public static CreateCategoryResult categoryNameNotUnique(EntityId id) {return new CreateCategoryResult(new ParentCategoryNotExists(id.value()));}

    public record Success(UUID id) { }

    public sealed interface Error extends ResultError { }
    public record CategoryNameNotUnique(UUID id) implements Error {
        @Override
        public String getMessage() {
            return "category name is not unique";
        }
    }
    public record ParentCategoryNotExists(UUID id) implements Error {
        @Override
        public String getMessage() {
            return "parent category with given id does not exist";
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
            NAME_EMPTY(InvalidField.NAME, InvalidReason.EMPTY),
            NAME_TOO_LONG(InvalidField.NAME, InvalidReason.TOO_LONG),
            NAME_TOO_SHORT(InvalidField.NAME, InvalidReason.TOO_SHORT);

            private final InvalidField field;
            private final InvalidReason reason;
        }

        public enum InvalidField {
            NAME
        }
        public enum InvalidReason {
            EMPTY,
            TOO_LONG,
            TOO_SHORT
        }

        public record ViolationDetails(InvalidField field, InvalidReason reason) { }
    }
}
