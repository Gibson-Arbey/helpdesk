package co.model.ticket.exception;

import co.model.exception.DomainException;
import co.model.exception.ErrorTypeEnum;

public class InvalidTicketStatusException extends DomainException {
    public InvalidTicketStatusException(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "INVALID_TICKET_STATUS";
    }

    @Override
    public ErrorTypeEnum getErrorType() {
        return ErrorTypeEnum.CONFLICT;
    }
}
