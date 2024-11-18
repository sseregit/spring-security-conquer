package spring.security.conquer;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

@RestController
public class IndexController {

    private final AsyncService asyncService;

    public IndexController(AsyncService asyncService) {
        this.asyncService = asyncService;
    }

    @GetMapping("/")
    String index() {
        return "index";
    }

    @GetMapping("/user")
    public String user() {
        return "user";
    }

    @GetMapping("/db")
    public String db() {
        return "db";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/callable")
    Callable<Authentication> call() {
        SecurityContext securityContext = SecurityContextHolder.getContextHolderStrategy().getContext();
        println(securityContext);
        return () -> {
            SecurityContext securityContext1 = SecurityContextHolder.getContextHolderStrategy().getContext();
            println(securityContext1);
            return securityContext1.getAuthentication();
        };
    }

    @GetMapping("/async")
    Authentication async() {
        SecurityContext securityContext1 = SecurityContextHolder.getContextHolderStrategy().getContext();
        println(securityContext1);

        asyncService.asyncMethod();

        return securityContext1.getAuthentication();
    }

    private void println(SecurityContext securityContext) {
        System.out.println("Thread = " + Thread.currentThread().getName() + "\nsecurityContext = " + securityContext);
    }
}