package co.model.exception;

public class InvalidFieldException extends DomainException {

    public InvalidFieldException(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "INVALID_FIELD";
    }

    @Override
    public ErrorTypeEnum getErrorType() {
        return ErrorTypeEnum.VALIDATION;
    }
}
