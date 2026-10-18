package co.usecase.user.exception;

import co.model.exception.DomainException;
import co.model.exception.ErrorTypeEnum;

public class InvalidPasswordException extends DomainException {

    public InvalidPasswordException(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "INVALID_PASSWORD";
    }

    @Override
    public ErrorTypeEnum getErrorType() {
        return ErrorTypeEnum.VALIDATION;
    }
}
