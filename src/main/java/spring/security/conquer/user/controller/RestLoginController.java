package spring.security.conquer.user.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class RestLoginController {

    @PostMapping("/api/login")
    String restLogin() {
        return "restLogin";
    }

}
