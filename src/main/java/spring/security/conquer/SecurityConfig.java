package spring.security.conquer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(auth -> auth.anyRequest().authenticated())
                .formLogin(form -> form
//                        .loginPage("/loginPage")
                                .loginProcessingUrl("/loginProc")
                                .defaultSuccessUrl("/", true)
                                .failureUrl("/failed")
                                .usernameParameter("userId")
                                .passwordParameter("passwd")
                                .successHandler((request, response, authentication) -> {
                                    System.out.println("authentication = " + authentication);
                                    response.sendRedirect("/home");
                                })
                                .failureHandler((request, response, exception) -> {
                                    System.out.println("exception = " + exception);
                                    response.sendRedirect("/login");
                                })
                                .permitAll()
                );

        return http.build();
    }

    @Bean
    InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        UserDetails user = User.withUsername("user")
                .password("{noop}1111")
                .authorities("ROLE_USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

}
