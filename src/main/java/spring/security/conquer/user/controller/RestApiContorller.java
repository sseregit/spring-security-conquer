package spring.security.conquer.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.security.conquer.domain.dto.AccountDto;

@RestController
@RequestMapping("/api")
class RestApiContorller {

    @GetMapping("/user")
    AccountDto restUser(@AuthenticationPrincipal AccountDto accountDto) {
        return accountDto;
    }

    @GetMapping("/manager")
    AccountDto restManager(@AuthenticationPrincipal AccountDto accountDto) {
        return accountDto;
    }

    @GetMapping("/admin")
    AccountDto restAdmin(@AuthenticationPrincipal AccountDto accountDto) {
        return accountDto;
    }

    @GetMapping("/logout")
    String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication();

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return "logout";
    }

}
