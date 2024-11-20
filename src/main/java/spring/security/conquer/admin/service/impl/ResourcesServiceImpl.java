package spring.security.conquer.admin.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.security.conquer.admin.repository.ResourcesRepository;
import spring.security.conquer.admin.service.ResourcesService;
import spring.security.conquer.domain.entity.Resources;
import spring.security.conquer.security.manager.CustomDynamicAuthorizationManager;

import java.util.List;

@Slf4j
@Service
public class ResourcesServiceImpl implements ResourcesService {

    private final ResourcesRepository resourcesRepository;
    private final CustomDynamicAuthorizationManager customDynamicAuthorizationManager;

    public ResourcesServiceImpl(ResourcesRepository resourcesRepository, CustomDynamicAuthorizationManager customDynamicAuthorizationManager) {
        this.resourcesRepository = resourcesRepository;
        this.customDynamicAuthorizationManager = customDynamicAuthorizationManager;
    }

    @Override
    @Transactional
    public Resources getResources(Long id) {
        return resourcesRepository.findById(id).orElse(new Resources());
    }

    @Override
    @Transactional
    public List<Resources> getResources() {
        return resourcesRepository.findAll(Sort.by(Sort.Order.asc("orderNum")));
    }

    @Override
    @Transactional
    public void createResources(Resources resources) {
        resourcesRepository.save(resources);
        customDynamicAuthorizationManager.reload();
    }

    @Override
    @Transactional
    public void deleteResources(Long id) {
        resourcesRepository.deleteById(id);
        customDynamicAuthorizationManager.reload();
    }
}
