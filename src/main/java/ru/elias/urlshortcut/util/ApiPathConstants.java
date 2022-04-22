package ru.elias.urlshortcut.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApiPathConstants {

    public static final String[] AUTH_WHITELIST = {
            "/",
            "/v2/api-docs",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/webjars/**"
    };

    public static final String API_V_1 = "/api/v1";

    public static final String AUTHENTICATION = "/authentication";

    public static final String LOGIN = "/login";

    public static final String SIGN_UP_ENDPOINT = API_V_1 + AUTHENTICATION + LOGIN;

    public static final String USER = "/user";

    public static final String CREATE_USER = "/create-user";

    public static final String REG_ENDPOINT = API_V_1 + USER + CREATE_USER;

    public static final String LINK = "/link";

    public static final String CONVERT_URL = "/convert-url";

    public static final String REDIRECT = "/redirect";

    public static final String STATISTIC = "/statistic";

    public static final String REDIRECT_ENDPOINT = API_V_1 + LINK + REDIRECT;

}
