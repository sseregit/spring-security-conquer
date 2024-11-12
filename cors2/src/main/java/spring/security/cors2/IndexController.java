package spring.security.cors2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
class IndexController {

    @GetMapping("/users")
    String users() {
        return """
                {
                    "username": "hong gil dong"
                }
                """;
    }

}
