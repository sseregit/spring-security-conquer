package spring.security.conquer;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.util.matcher.RequestMatcher;

class CustomRequestMatcher implements RequestMatcher {

    private final String urlPattern;

    public CustomRequestMatcher(String urlPattern) {
        this.urlPattern = urlPattern;
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return requestURI.startsWith(urlPattern);
    }
}
