package spring.security.conquer;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    private final SecurityContextService securityContextService;

    public IndexController(SecurityContextService securityContextService) {
        this.securityContextService = securityContextService;
    }

    @GetMapping("/")
    public String index() {
        securityContextService.securityContext();
        return "index";
    }

    @GetMapping("/loginPage")
    public String loginPage() {
        return "loginPage";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/anonymous")
    public String anonymous() {
        return "anonymous";
    }

    @GetMapping("/anonymous-context")
    public String anonymousContext(@CurrentSecurityContext SecurityContext context) {
        return context.getAuthentication().getName();
    }

    @GetMapping("/authentication")
    public String authentication(Authentication authentication) {

        if (authentication instanceof AnonymousAuthenticationToken) {
            return "anonymous";
        } else {
            return "not anonymous";
        }
    }

    @GetMapping("/logout-success")
    public String logoutSuccess() {
        return "logout-success";
    }

}
