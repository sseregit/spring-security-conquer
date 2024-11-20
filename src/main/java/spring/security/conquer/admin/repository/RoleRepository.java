package spring.security.conquer.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import spring.security.conquer.domain.entity.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(String roleName);

    @Override
    void delete(Role role);

    @Query("select r from Role r where r.isExpression = 'N'")
    List<Role> findAllRolesWithoutExpression();
}
