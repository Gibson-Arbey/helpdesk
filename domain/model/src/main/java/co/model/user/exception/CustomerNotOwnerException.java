package co.model.user.exception;

import co.model.exception.DomainException;
import co.model.exception.ErrorTypeEnum;

public class CustomerNotOwnerException extends DomainException {
    public CustomerNotOwnerException(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "CUSTOMER_NOT_OWNER";
    }

    @Override
    public ErrorTypeEnum getErrorType() {
        return ErrorTypeEnum.FORBIDDEN;
    }
}
