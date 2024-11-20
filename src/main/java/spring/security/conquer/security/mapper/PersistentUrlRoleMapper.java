package spring.security.conquer.security.mapper;

import spring.security.conquer.admin.repository.ResourcesRepository;

import java.util.LinkedHashMap;
import java.util.Map;

public class PersistentUrlRoleMapper implements UrlRoleMapper {

    private final Map<String, String> urlRoleMappings = new LinkedHashMap<>();
    private final ResourcesRepository resourcesRepository;

    public PersistentUrlRoleMapper(ResourcesRepository resourcesRepository) {
        this.resourcesRepository = resourcesRepository;
    }

    @Override
    public Map<String, String> getUrlRoleMappings() {

        urlRoleMappings.clear();

        resourcesRepository.findAllResources()
                .forEach(resource -> {
                    resource.getRoleSet().forEach(role -> {
                        urlRoleMappings.put(resource.getResourceName(), role.getRoleName());
                    });
                });
        return urlRoleMappings;
    }
}
