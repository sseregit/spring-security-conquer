package spring.security.conquer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class ViewController {

    @GetMapping("/form")
    String form() {
        return "form";
    }

    @GetMapping("/cookie")
    String cookie() {
        return "cookie";
    }
}
