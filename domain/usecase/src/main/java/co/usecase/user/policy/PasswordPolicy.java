package co.usecase.user.policy;

import co.usecase.user.exception.InvalidPasswordException;

public class PasswordPolicy {

    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#._-]).{8,}$";

    private PasswordPolicy() {}

    public static void validate(String rawPassword) {
        if (rawPassword == null || rawPassword.isBlank()) {
            throw new InvalidPasswordException("Password must not be empty");
        }
        if (!rawPassword.matches(PASSWORD_REGEX)) {
            throw new InvalidPasswordException(
                    "Password must meet the following requirements: minimum 8 characters, include uppercase and lowercase letters, at least one number, and one special character (@$!%*?&#._-)."
            );
        }
    }
}
