package spring.security.conquer.security.configs;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import spring.security.conquer.security.handler.FormAccessDeniedHandler;

@EnableWebSecurity
@Configuration
class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource;

    public SecurityConfig(AuthenticationProvider authenticationProvider, AuthenticationSuccessHandler authenticationSuccessHandler, AuthenticationFailureHandler authenticationFailureHandler, AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource) {
        this.authenticationProvider = authenticationProvider;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.authenticationDetailsSource = authenticationDetailsSource;
    }

    @Bean
    @Order(1)
    SecurityFilterChain restSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/login")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/images/**", "/js/**", "/favicon.*", "/*/icon-*").permitAll()
                        .anyRequest().permitAll())
                .csrf(AbstractHttpConfigurer::disable)
        ;

        return http.build();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/images/**", "/js/**", "/favicon.*", "/*/icon-*").permitAll()
                        .requestMatchers("/", "/signup", "/login*").permitAll()
                        .requestMatchers("/user").hasAuthority("ROLE_USER")
                        .requestMatchers("/manager").hasAuthority("ROLE_MANAGER")
                        .requestMatchers("/admin").hasAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login").permitAll()
                        .authenticationDetailsSource(authenticationDetailsSource)
                        .successHandler(authenticationSuccessHandler)
                        .failureHandler(authenticationFailureHandler)
                )
                .authenticationProvider(authenticationProvider)
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(new FormAccessDeniedHandler("/denied")))
        ;

        return http.build();
    }

}
