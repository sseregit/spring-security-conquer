package spring.security.conquer;

import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.core.Authentication;

class CustomAuthenticationSuccessEvent extends AbstractAuthenticationEvent {

    public CustomAuthenticationSuccessEvent(Authentication authentication) {
        super(authentication);
    }
}
