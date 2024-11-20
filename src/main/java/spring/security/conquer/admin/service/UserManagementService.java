package spring.security.conquer.admin.service;

import spring.security.conquer.domain.dto.AccountDto;
import spring.security.conquer.domain.entity.Account;

import java.util.List;

public interface UserManagementService {

    void modifyUser(AccountDto accountDto);

    List<Account> getUsers();

    AccountDto getUser(Long id);

    void deleteUser(Long id);
}
