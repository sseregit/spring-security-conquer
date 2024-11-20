package spring.security.conquer.security.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import spring.security.conquer.admin.service.RoleHierarchyService;

@Configuration
public class AuthConfig {

    private boolean flag = true;

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    RoleHierarchyImpl roleHierarchyService(RoleHierarchyService roleHierarchyService) {

        if (flag) {
            roleHierarchyService.init();
            flag = false;
        }

        String allHierarchy = roleHierarchyService.findAllHierarchy();
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy(allHierarchy);

        return roleHierarchy;
    }
}
