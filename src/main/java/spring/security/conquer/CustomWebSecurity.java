package spring.security.conquer;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
class CustomWebSecurity {

    public boolean check(Authentication authentication, HttpServletRequest request) {
        System.out.println("authentication = " + authentication);
        return authentication.isAuthenticated();
    }
}
