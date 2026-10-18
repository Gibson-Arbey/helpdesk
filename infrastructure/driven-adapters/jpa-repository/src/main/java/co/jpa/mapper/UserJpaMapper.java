package co.jpa.mapper;

import co.jpa.entity.UserJpaEntity;
import co.model.user.User;
import co.model.user.valueobject.Email;
import co.model.user.valueobject.Password;

public class UserJpaMapper {

    public static UserJpaEntity toEntity(User user) {
        if (user == null) {
            return null;
        }
        return UserJpaEntity.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail().value())
                .password(user.getPassword().value())
                .role(user.getRole())
                .build();
    }

    public static User toDomain(UserJpaEntity user) {
        if (user == null) {
            return null;
        }
        return User.restore(user.getId(), user.getName(), new Email(user.getEmail()), new Password(user.getPassword()), user.getRole());
    }
}
