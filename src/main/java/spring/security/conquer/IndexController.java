package spring.security.conquer;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RestController
public class IndexController {

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

    @GetMapping("/login")
    String login(HttpServletRequest request, MemberDto memberDto) throws ServletException {
        request.login(memberDto.username(), memberDto.passowrd());
        System.out.println("IndexController.login");
        return "login";
    }

    @GetMapping("/users")
    List<MemberDto> users(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean authenticate = request.authenticate(response);

        if(authenticate) {
            return List.of(new MemberDto("user", "1111"));
        }
        return Collections.emptyList();
    }

    record MemberDto(
            String username,
            String passowrd
    ) {
    }
}