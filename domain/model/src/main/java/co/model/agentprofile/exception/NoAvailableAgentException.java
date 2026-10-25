package co.model.agentprofile.exception;

import co.model.exception.DomainException;
import co.model.exception.ErrorTypeEnum;

public class NoAvailableAgentException extends DomainException {
    public NoAvailableAgentException(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "NO_AVAILABLE_AGENT";
    }

    @Override
    public ErrorTypeEnum getErrorType() {
        return ErrorTypeEnum.CONFLICT;
    }
}
