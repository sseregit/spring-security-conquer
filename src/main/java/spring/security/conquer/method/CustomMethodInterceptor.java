package spring.security.conquer.method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.nio.file.AccessDeniedException;

public class CustomMethodInterceptor implements MethodInterceptor {

    private final AuthorizationManager<MethodInvocation> manager;

    public CustomMethodInterceptor(AuthorizationManager<MethodInvocation> manager) {
        this.manager = manager;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (manager.check(() -> authentication, invocation).isGranted()) {
            return invocation.proceed();
        }

        throw new AccessDeniedException("Access denied");
    }
}
