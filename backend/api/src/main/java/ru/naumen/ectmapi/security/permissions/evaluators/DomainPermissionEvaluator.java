package ru.naumen.ectmapi.security.permissions.evaluators;

import org.springframework.security.core.Authentication;

public interface DomainPermissionEvaluator {
    boolean hasPermission(Authentication authentication, Long targetId, String permission);
}
