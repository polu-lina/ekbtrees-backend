package ru.naumen.ectmapi.security.permissions;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import ru.naumen.ectmapi.security.permissions.evaluators.MainPermissionEvaluator;

@Configuration
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true
)
public class MethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {

    private final MainPermissionEvaluator permissionEvaluator;
    private final ApplicationContext context;

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(permissionEvaluator);
        expressionHandler.setApplicationContext(context);
        return expressionHandler;
    }
}
