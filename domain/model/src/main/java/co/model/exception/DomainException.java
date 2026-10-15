package co.model.exception;

public abstract class DomainException extends RuntimeException {

    protected DomainException(String message) {
        super(message);
    }

    public abstract String getCode();

    public abstract ErrorTypeEnum getErrorType();
}
