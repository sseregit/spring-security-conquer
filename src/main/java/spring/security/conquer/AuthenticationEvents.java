package spring.security.conquer;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.*;
import org.springframework.stereotype.Component;

@Component
class AuthenticationEvents {

    @EventListener
    void onSuccess(AuthenticationSuccessEvent success) {
        System.out.println("AuthenticationEvents.AuthenticationSuccessEvent");
        System.out.println("success = " + success.getAuthentication().getName());
    }

    @EventListener
    void onFailure(AbstractAuthenticationFailureEvent failures) {
        System.out.println("AuthenticationEvents.AbstractAuthenticationFailureEvent");
        System.out.println("failures = " + failures.getAuthentication().getName());
    }

    @EventListener
    void onSuccess(InteractiveAuthenticationSuccessEvent success) {
        System.out.println("AuthenticationEvents.InteractiveAuthenticationSuccessEvent");
        System.out.println("success = " + success.getAuthentication().getName());
    }

    @EventListener
    void onSuccess(CustomAuthenticationSuccessEvent success) {
        System.out.println("AuthenticationEvents.CustomAuthenticationSuccessEvent");
        System.out.println("success = " + success.getAuthentication().getName());
    }

    @EventListener
    void onFailure(AuthenticationFailureBadCredentialsEvent failures) {
        System.out.println("AuthenticationEvents.AuthenticationFailureBadCredentialsEvent");
        System.out.println("failures = " + failures.getAuthentication().getName());
    }

    @EventListener
    void onFailure(AuthenticationFailureProviderNotFoundEvent failures) {
        System.out.println("AuthenticationEvents.AuthenticationFailureProviderNotFoundEvent");
        System.out.println("failures = " + failures.getAuthentication().getName());
    }

    @EventListener
    void onFailure(CustomAuthenticationFailureEvent failures) {
        System.out.println("AuthenticationEvents.CustomAuthenticationFailureEvent");
        System.out.println("failures = " + failures.getAuthentication().getName());
    }

    @EventListener
    public void onFailure(DefaultAuthenticationFailureEvent failures) {
        System.out.println("AuthenticationEvents.DefaultAuthenticationFailureEvent");
        System.out.println("failures = " + failures.getException().getMessage());
    }

}
