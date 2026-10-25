package co.model.agentprofile.exception;

import co.model.exception.DomainException;
import co.model.exception.ErrorTypeEnum;

public class AgentNotFoundException extends DomainException {
    public AgentNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "AGENT_NOT_FOUND";
    }

    @Override
    public ErrorTypeEnum getErrorType() {
        return ErrorTypeEnum.NOT_FOUND;
    }
}
