package spring.security.conquer.admin.service;

import spring.security.conquer.domain.entity.Resources;

import java.util.List;

public interface ResourcesService {
    Resources getResources(Long id);

    List<Resources> getResources();

    void createResources(Resources resources);

    void deleteResources(Long id);
}
