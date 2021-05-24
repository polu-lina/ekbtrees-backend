package ru.naumen.ectmauth.constants;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public abstract class AuthConstants {
    public static final Set<String> DEFAULT_ROLE_NAMES = Set.of("volunteer");
    public static final long COOKIES_MAX_AGE = TimeUnit.DAYS.toSeconds(90);
}
