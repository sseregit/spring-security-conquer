package spring.security.conquer;

import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureProviderNotFoundEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final ApplicationContext applicationContext;

    public CustomAuthenticationProvider(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        if (!authentication.getName().equals("user")) {
            applicationContext.publishEvent(
                    new AuthenticationFailureProviderNotFoundEvent(authentication, new BadCredentialsException("Bad credentials"))
            );

            throw new BadCredentialsException("Bad credentials");
        }

        UserDetails user = User.withUsername("user").password("{noop}1111").roles("USER").build();

        return new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
