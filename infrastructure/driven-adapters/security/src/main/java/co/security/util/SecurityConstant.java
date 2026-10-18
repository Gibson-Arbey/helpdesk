package co.security.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "security")
public class SecurityConstant {

    private String jwtKeyPrivate;
    private String jwtUserGenerator;
    private Integer jwtExpiration;
}
