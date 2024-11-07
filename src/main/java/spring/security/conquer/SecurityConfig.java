package spring.security.conquer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        http.authorizeRequests(auth -> auth
                        .requestMatchers("/anonymous").hasRole("GUEST")
                        .requestMatchers("/anonymous-context", "/authentication").permitAll()
                        .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
                .anonymous(anonymous -> anonymous
                        .principal("guest")
                        .authorities("ROLE_GUEST")
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
