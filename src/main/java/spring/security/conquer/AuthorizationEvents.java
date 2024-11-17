package spring.security.conquer;

import org.springframework.context.event.EventListener;
import org.springframework.security.authorization.event.AuthorizationDeniedEvent;
import org.springframework.security.authorization.event.AuthorizationEvent;
import org.springframework.security.authorization.event.AuthorizationGrantedEvent;
import org.springframework.stereotype.Component;

@Component
class AuthorizationEvents {

    @EventListener
    void onAuthorization(AuthorizationEvent event) {
        System.out.println("AuthorizationEvents.AuthorizationEvent");
        System.out.println("event = " + event.getAuthentication().get().getAuthorities());
    }

    @EventListener
    void onAuthorization(AuthorizationDeniedEvent failure) {
        System.out.println("AuthorizationEvents.AuthorizationDeniedEvent");
        System.out.println("event = " + failure.getAuthentication().get().getAuthorities());
    }

    @EventListener
    void onAuthorization(AuthorizationGrantedEvent success) {
        System.out.println("AuthorizationEvents.AuthorizationGrantedEvent");
        System.out.println("event = " + success.getAuthentication().get().getAuthorities());
    }

}
