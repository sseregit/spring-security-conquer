package spring.security.conquer.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class LoginController {

    @GetMapping("/login")
    String login() {
        return "login/login";
    }

    @GetMapping("/signup")
    String signup() {
        return "login/signup";
    }
}
