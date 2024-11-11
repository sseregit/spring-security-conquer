package spring.security.conquer;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

    public LoginController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        securityContextRepository = new HttpSessionSecurityContextRepository();
    }

    @PostMapping("/login")
    Authentication login(@RequestBody LoginRequest login, HttpServletRequest request, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken unauthenticated =
                UsernamePasswordAuthenticationToken.unauthenticated(login.username(), login.password());

        Authentication authenticate = authenticationManager.authenticate(unauthenticated);

        SecurityContext securityContext = SecurityContextHolder.getContextHolderStrategy().createEmptyContext();
        securityContext.setAuthentication(authenticate);
        SecurityContextHolder.getContextHolderStrategy().setContext(securityContext);

        securityContextRepository.saveContext(securityContext, request, response);

        return authenticate;
    }

    record LoginRequest(
            String username,
            String password
    ) {
    }
}
