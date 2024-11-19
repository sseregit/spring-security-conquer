package spring.security.conquer.user.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

}
