package spring.security.conquer.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.security.conquer.user.domain.entity.Account;

public interface UserRepository extends JpaRepository<Account, Long> {
}
