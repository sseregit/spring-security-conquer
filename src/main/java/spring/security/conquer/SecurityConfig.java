package spring.security.conquer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultHttpSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

@EnableWebSecurity
@Configuration
class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, ApplicationContext context) throws Exception {

        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers(new CustomRequestMatcher("/admin")).hasAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
        ;
        return http.build();
    }


    SecurityFilterChain cookieSecurityFilterChain(HttpSecurity http, ApplicationContext context) throws Exception {

        DefaultHttpSecurityExpressionHandler expressionHandler = new DefaultHttpSecurityExpressionHandler();
        expressionHandler.setApplicationContext(context);

        WebExpressionAuthorizationManager authorizationManager = new WebExpressionAuthorizationManager("@customWebSecurity.check(authentication, request)");
        authorizationManager.setExpressionHandler(expressionHandler);

        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/custom/**").access(authorizationManager)
                        .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
        ;
        return http.build();
    }

    @Bean
    InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        UserDetails user = User.withUsername("user")
                .password("{noop}1111")
                .authorities("ROLE_USER")
                .build();

        UserDetails manager = User.withUsername("manager")
                .password("{noop}1111")
                .authorities("ROLE_MANAGER")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password("{noop}1111")
                .authorities("ROLE_ADMIN", "ROLE_WRITE")
                .build();

        return new InMemoryUserDetailsManager(user, manager, admin);
    }

}
