package spring.security.conquer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
        ;
        return http.build();
    }

    @Bean
    @Order(1)
    SecurityFilterChain securityFilterChain2(HttpSecurity http) throws Exception {

        http.securityMatchers(matchers -> matchers.requestMatchers("/api/**", "/oauth/**"))
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll())
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
