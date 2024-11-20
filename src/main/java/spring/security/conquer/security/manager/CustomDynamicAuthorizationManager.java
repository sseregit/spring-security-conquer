package spring.security.conquer.security.manager;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcherEntry;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import spring.security.conquer.admin.repository.ResourcesRepository;
import spring.security.conquer.security.mapper.PersistentUrlRoleMapper;
import spring.security.conquer.security.service.DynamicAuthorizationService;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Component
public class CustomDynamicAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private static final AuthorizationDecision ACCESS = new AuthorizationDecision(true);
    private final HandlerMappingIntrospector handlerMappingIntrospector;
    private final ResourcesRepository resourcesRepository;
    List<RequestMatcherEntry<AuthorizationManager<RequestAuthorizationContext>>> mappings;

    public CustomDynamicAuthorizationManager(HandlerMappingIntrospector handlerMappingIntrospector, ResourcesRepository resourcesRepository) {
        this.handlerMappingIntrospector = handlerMappingIntrospector;
        this.resourcesRepository = resourcesRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void mappings() {
        DynamicAuthorizationService dynamicAuthorizationService = new DynamicAuthorizationService(new PersistentUrlRoleMapper(resourcesRepository));

        mappings = dynamicAuthorizationService.getUrlRoleMappings()
                .entrySet().stream()
                .map(entry -> new RequestMatcherEntry<>(
                        new MvcRequestMatcher(handlerMappingIntrospector, entry.getKey()),
                        customAuthorizationManager(entry.getValue())))
                .collect(Collectors.toList());

    }

    private AuthorizationManager<RequestAuthorizationContext> customAuthorizationManager(String role) {

        if (role != null) {
            if (role.startsWith("ROLE_")) {
                return AuthorityAuthorizationManager.hasAuthority(role);
            }

            return new WebExpressionAuthorizationManager(role);
        }

        return null;
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext request) {
        for (RequestMatcherEntry<AuthorizationManager<RequestAuthorizationContext>> mapping : mappings) {

            RequestMatcher matcher = mapping.getRequestMatcher();
            RequestMatcher.MatchResult matchResult = matcher.matcher(request.getRequest());

            if (matchResult.isMatch()) {
                AuthorizationManager<RequestAuthorizationContext> manager = mapping.getEntry();
                return manager.check(authentication, new RequestAuthorizationContext(request.getRequest(), matchResult.getVariables()));
            }
        }
        return ACCESS;
    }

    @Override
    public void verify(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        AuthorizationManager.super.verify(authentication, object);
    }
}
