package spring.security.conquer.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.security.conquer.admin.repository.RoleHierarchyRepository;
import spring.security.conquer.admin.service.RoleHierarchyService;
import spring.security.conquer.domain.entity.RoleHierarchy;

import java.util.Iterator;
import java.util.List;

@Service
public class RoleHierarchyServiceImpl implements RoleHierarchyService {
    private RoleHierarchyRepository roleHierarchyRepository;

    @Autowired
    private void setRoleHierarchyServiceImpl(RoleHierarchyRepository roleHierarchyRepository) {
        this.roleHierarchyRepository = roleHierarchyRepository;
    }

    @Transactional
    @Override
    public String findAllHierarchy() {

        List<RoleHierarchy> rolesHierarchy = roleHierarchyRepository.findAll();

        Iterator<RoleHierarchy> itr = rolesHierarchy.iterator();
        StringBuilder hierarchyRole = new StringBuilder();

        while (itr.hasNext()) {
            RoleHierarchy roleHierarchy = itr.next();
            if (roleHierarchy.getParent() != null) {
                hierarchyRole.append(roleHierarchy.getParent().getRoleName());
                hierarchyRole.append(" > ");
                hierarchyRole.append(roleHierarchy.getRoleName());
                hierarchyRole.append("\n");
            }
        }
        return hierarchyRole.toString();
    }

    @Override
    @Transactional
    public void init() {
        RoleHierarchy roleAdmin = RoleHierarchy
                .builder()
                .id(1L)
                .roleName("ROLE_ADMIN")
                .parent(null)
                .build();

        roleHierarchyRepository.save(roleAdmin);

        RoleHierarchy roleManager = RoleHierarchy
                .builder()
                .id(2L)
                .roleName("ROLE_MANAGER")
                .parent(roleAdmin)
                .build();
        roleHierarchyRepository.save(roleManager);

        RoleHierarchy roleDBA = RoleHierarchy
                .builder()
                .id(3L)
                .roleName("ROLE_DBA")
                .parent(roleManager)
                .build();
        roleHierarchyRepository.save(roleDBA);

        RoleHierarchy roleUser1 = RoleHierarchy
                .builder()
                .id(4L)
                .roleName("ROLE_USER")
                .parent(roleManager)
                .build();
        roleHierarchyRepository.save(roleUser1);

        RoleHierarchy roleUser2 = RoleHierarchy
                .builder()
                .id(5L)
                .roleName("ROLE_USER")
                .parent(roleDBA)
                .build();
        roleHierarchyRepository.save(roleUser2);
    }
}