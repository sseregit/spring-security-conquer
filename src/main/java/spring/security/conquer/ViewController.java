package spring.security.conquer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class ViewController {

    @GetMapping("/method")
    String method() {
        return "method";
    }
}
