package co.model.ticket.exception;

import co.model.exception.DomainException;
import co.model.exception.ErrorTypeEnum;

public class AgentNotAssignedToTicketException extends DomainException {
    public AgentNotAssignedToTicketException(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "AGENT_NOT_ASSIGNED_TO_TICKET";
    }

    @Override
    public ErrorTypeEnum getErrorType() {
        return ErrorTypeEnum.FORBIDDEN;
    }
}
