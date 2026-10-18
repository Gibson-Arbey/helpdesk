package co.model.user.valueobject;

import co.model.exception.InvalidFieldException;

public class Password {

    private final String value;

    public Password(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidFieldException("Password must not be null or empty");
        }
        this.value = value;
    }

    public String value() {
        return value;
    }

}
