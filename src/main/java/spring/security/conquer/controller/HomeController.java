package spring.security.conquer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class HomeController {

    @GetMapping("/")
    String dashboard() {
        return "/dashboard";
    }

    @GetMapping("/api")
    String restDashboard() {
        return "rest/dashboard";
    }

    @GetMapping("/user")
    String user() {
        return "/user";
    }

    @GetMapping("/manager")
    String manager() {
        return "/manager";
    }

    @GetMapping("/admin")
    String admin() {
        return "/admin";
    }

}
