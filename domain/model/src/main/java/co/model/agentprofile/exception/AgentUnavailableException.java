package co.model.agentprofile.exception;

import co.model.exception.DomainException;
import co.model.exception.ErrorTypeEnum;

public class AgentUnavailableException extends DomainException {
    public AgentUnavailableException(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "AGENT_UNAVAILABLE";
    }

    @Override
    public ErrorTypeEnum getErrorType() {
        return ErrorTypeEnum.CONFLICT;
    }
}
