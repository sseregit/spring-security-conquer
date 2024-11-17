package spring.security.conquer;

import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomAuthenticationProvider2 implements AuthenticationProvider {

    private final AuthenticationEventPublisher authenticationEventPublisher;

    public CustomAuthenticationProvider2(AuthenticationEventPublisher authenticationEventPublisher) {
        this.authenticationEventPublisher = authenticationEventPublisher;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        if (!authentication.getName().equals("user")) {
            authenticationEventPublisher.publishAuthenticationFailure(
                    new BadCredentialsException("Bad credentials"), authentication
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
