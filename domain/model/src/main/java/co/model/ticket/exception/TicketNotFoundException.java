package co.model.ticket.exception;

import co.model.exception.DomainException;
import co.model.exception.ErrorTypeEnum;

public class TicketNotFoundException extends DomainException {
    public TicketNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "TICKET_NOT_FOUND";
    }

    @Override
    public ErrorTypeEnum getErrorType() {
        return ErrorTypeEnum.NOT_FOUND;
    }
}
