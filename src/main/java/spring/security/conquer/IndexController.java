package spring.security.conquer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/user")
    public String user() {
        return "user";
    }

    @GetMapping("/custom")
    public String custom() {
        return "custom";
    }

    @GetMapping("/user/{name}")
    public String user(@PathVariable String name) {
        return name;
    }

    @GetMapping("/admin/db")
    public String admin() {
        return "admin";
    }

}
