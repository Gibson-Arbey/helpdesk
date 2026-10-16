package co.jpa.mapper;

import co.jpa.entity.UserJpaEntity;
import co.model.user.User;

public class UserJpaMapper {

    public static UserJpaEntity toEntity(User user) {
        if (user == null) {
            return null;
        }
        return UserJpaEntity.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

    public static User toDomain(UserJpaEntity user) {
        if (user == null) {
            return null;
        }
        return User.restore(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getRole());
    }
}
