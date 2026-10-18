package co.security.adapter;

import co.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceAdapter implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        var user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        if (user.getRole() == null) {
            throw new UsernameNotFoundException("Role not found for user with email: " + email);
        }
        String authority = user.getRole().toString().toUpperCase(Locale.ROOT).startsWith("ROLE_")
                ? user.getRole().toString().toUpperCase(Locale.ROOT)
                : "ROLE_" + user.getRole().toString().toUpperCase(Locale.ROOT);

        return User.builder()
                .username(user.getEmail().value())
                .password(user.getPassword().value())
                .authorities(authority)
                .build();
    }
}
