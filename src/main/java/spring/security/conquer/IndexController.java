package spring.security.conquer;

import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class IndexController {

    AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();

    @GetMapping("/")
    String index() {
        Authentication authentication = SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication();
        return authenticationTrustResolver.isAnonymous(authentication) ? "anonymous" : "authenticated";
    }

    @GetMapping("/user")
    public User user(@AuthenticationPrincipal User user) {
        return user;
    }

    @GetMapping("/username")
    public Set<GrantedAuthority> user(@AuthenticationPrincipal(expression = "authorities") Set<GrantedAuthority> authorities) {
        return authorities;
    }

    @GetMapping("/user2")
    public User user2(@CurrentUser User user) {
        return user;
    }

    @GetMapping("/username2")
    public String user2(@CurrentUsername String username) {
        return username;
    }


    @GetMapping("/db")
    public String db() {
        return "db";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

}