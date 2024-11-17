package spring.security.conquer;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authorization.AuthorityAuthorizationDecision;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationEventPublisher;
import org.springframework.security.authorization.event.AuthorizationGrantedEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.function.Supplier;

public class MyAuthorizationEventPublisher implements AuthorizationEventPublisher {

    private final AuthorizationEventPublisher delegate;

    private final ApplicationEventPublisher applicationEventPublisher;

    public MyAuthorizationEventPublisher(AuthorizationEventPublisher delegate, ApplicationEventPublisher applicationEventPublisher) {
        this.delegate = delegate;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public <T> void publishAuthorizationEvent(Supplier<Authentication> authentication, T object, AuthorizationDecision decision) {
        if (decision == null) {
            return;
        }

        if (!decision.isGranted()) {
            this.delegate.publishAuthorizationEvent(authentication, object, decision);
            return;
        }

        if (shouldThisEventBePublished(decision)) {
            AuthorizationGrantedEvent<T> granted = new AuthorizationGrantedEvent<>(authentication, object, decision);
            applicationEventPublisher.publishEvent(granted);
        }
    }

    boolean shouldThisEventBePublished(AuthorizationDecision decision) {
        if (!(decision instanceof AuthorityAuthorizationDecision)) {
            return false;
        }
        Collection<GrantedAuthority> authorities = ((AuthorityAuthorizationDecision) decision).getAuthorities();

        for (GrantedAuthority authority : authorities) {
            if ("ROLE_ADMIN".equals(authority.getAuthority())) {
                return true;
            }
        }
        return false;
    }
}
