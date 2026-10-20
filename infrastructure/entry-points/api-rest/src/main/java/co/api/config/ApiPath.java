package co.api.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiPath {

    public static final String ROUTE_USER = "api/user";
    public static final String ROUTE_AUTH = "api/auth";

    public static final String V1 = "1";
}
