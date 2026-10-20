package co.model.user.exception;

import co.model.exception.DomainException;
import co.model.exception.ErrorTypeEnum;

public class UserNotFoundException extends DomainException {
    public UserNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "USER_NOT_FOUND";
    }

    @Override
    public ErrorTypeEnum getErrorType() {
        return ErrorTypeEnum.NOT_FOUND;
    }
}
