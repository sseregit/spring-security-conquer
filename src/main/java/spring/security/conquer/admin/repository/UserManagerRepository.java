package spring.security.conquer.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.security.conquer.domain.entity.Account;

public interface UserManagerRepository extends JpaRepository<Account, Long> {
}
