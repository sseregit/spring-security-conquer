package spring.security.conquer.security.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import spring.security.conquer.domain.dto.AccountContext;
import spring.security.conquer.security.details.FormAuthenticationDetails;
import spring.security.conquer.security.exception.SecretException;

@Component
public class FormAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public FormAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String loginId = authentication.getName();
        String password = (String) authentication.getCredentials();

        AccountContext accountContext = (AccountContext) userDetailsService.loadUserByUsername(loginId);

        if (!passwordEncoder.matches(password, accountContext.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        String secretKey = ((FormAuthenticationDetails) authentication.getDetails()).getSecretKey();

        if (secretKey == null || !secretKey.equals("secret")) {
            throw new SecretException("Invalid secret");
        }

        return new UsernamePasswordAuthenticationToken(accountContext.getAccountDto(), password, accountContext.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
