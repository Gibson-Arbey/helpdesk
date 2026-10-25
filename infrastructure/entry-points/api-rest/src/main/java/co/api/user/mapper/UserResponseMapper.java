package co.api.user.mapper;

import co.api.user.response.UserResponse;
import co.model.user.User;

public class UserResponseMapper {

    public static UserResponse toResponse(User user) {
        if (user == null) {
            return null;
        }
        return new UserResponse(user.getId(), user.getName(), user.getEmail().value());
    }
}
