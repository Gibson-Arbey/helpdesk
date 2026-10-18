package co.model.user.exception;

import co.model.exception.DomainException;
import co.model.exception.ErrorTypeEnum;

public class InvalidEmailException extends DomainException {
    public InvalidEmailException(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "INVALID_EMAIL";
    }

    @Override
    public ErrorTypeEnum getErrorType() {
        return ErrorTypeEnum.VALIDATION;
    }
}
