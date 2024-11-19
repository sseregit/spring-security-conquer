package spring.security.conquer.security.details;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class FormAuthenticationDetails extends WebAuthenticationDetails {

    private final String secretKey;

    public FormAuthenticationDetails(HttpServletRequest request) {
        super(request);
        this.secretKey = request.getParameter("secret_key");
    }

    public String getSecretKey() {
        return secretKey;
    }
}
