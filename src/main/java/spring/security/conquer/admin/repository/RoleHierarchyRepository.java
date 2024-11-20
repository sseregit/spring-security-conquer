package spring.security.conquer.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.security.conquer.domain.entity.RoleHierarchy;

public interface RoleHierarchyRepository extends JpaRepository<RoleHierarchy, Long> {
}
