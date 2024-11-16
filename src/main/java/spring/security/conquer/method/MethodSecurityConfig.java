package spring.security.conquer.method;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Advisor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.method.AuthorizationManagerAfterMethodInterceptor;
import org.springframework.security.authorization.method.AuthorizationManagerBeforeMethodInterceptor;
import org.springframework.security.authorization.method.MethodInvocationResult;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import spring.security.conquer.Account;

import java.util.function.Supplier;

@EnableMethodSecurity(prePostEnabled = false)
@Configuration
class MethodSecurityConfig {

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    Advisor preAuthorize() {
        return AuthorizationManagerBeforeMethodInterceptor.preAuthorize(new MyPreAuthorizationManager());
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    Advisor postAuthorize() {
        return AuthorizationManagerAfterMethodInterceptor.postAuthorize(new MyPostAuthorizationManager());
    }

    static class MyPreAuthorizationManager implements AuthorizationManager<MethodInvocation> {

        @Override
        public AuthorizationDecision check(Supplier<Authentication> authentication, MethodInvocation object) {

            Authentication auth = authentication.get();

            if (auth instanceof AnonymousAuthenticationToken) return new AuthorizationDecision(false);

            return new AuthorizationDecision(auth.isAuthenticated());
        }
    }

    static class MyPostAuthorizationManager implements AuthorizationManager<MethodInvocationResult> {

        @Override
        public AuthorizationDecision check(Supplier<Authentication> authentication, MethodInvocationResult object) {

            Authentication auth = authentication.get();

            if (auth instanceof AnonymousAuthenticationToken) return new AuthorizationDecision(false);

            Account account = (Account) object.getResult();

            return new AuthorizationDecision(account.owner().equals(auth.getName()));
        }
    }
}
