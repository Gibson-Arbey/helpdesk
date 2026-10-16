package co.model.user.exception;

import co.model.exception.DomainException;
import co.model.exception.ErrorTypeEnum;

public class EmailAlreadyExistsException extends DomainException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "EMAIL_ALREADY_EXISTS";
    }

    @Override
    public ErrorTypeEnum getErrorType() {
        return ErrorTypeEnum.VALIDATION;
    }
}
