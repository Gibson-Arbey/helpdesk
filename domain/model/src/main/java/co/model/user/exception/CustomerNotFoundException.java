package co.model.user.exception;

import co.model.exception.DomainException;
import co.model.exception.ErrorTypeEnum;

public class CustomerNotFoundException extends DomainException {
    public CustomerNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "CUSTOMER_NOT_FOUND";
    }

    @Override
    public ErrorTypeEnum getErrorType() {
        return ErrorTypeEnum.NOT_FOUND;
    }
}
