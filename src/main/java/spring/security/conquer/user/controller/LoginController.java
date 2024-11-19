package spring.security.conquer.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import spring.security.conquer.domain.dto.AccountDto;

@Controller
class LoginController {

    @GetMapping("/login")
    String login(@ModelAttribute ErrorLogRecord errorLogRecord) {
        System.out.println("errorLogRecord = " + errorLogRecord);
        return "login/login";
    }

    @GetMapping("/signup")
    String signup() {
        return "login/signup";
    }

    @GetMapping("/logout")
    String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication();

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return "redirect:/login";
    }

    @GetMapping("/denied")
    String accessDenied(@RequestParam String exception, @AuthenticationPrincipal AccountDto accountDto, Model model) {

        model.addAttribute("username", accountDto.getUsername());
        model.addAttribute("exception", exception);

        return "login/denied";
    }


    record ErrorLogRecord(
            String error,
            String exception
    ) {

    }

}
