package spring.security.conquer.admin.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.security.conquer.admin.repository.RoleRepository;
import spring.security.conquer.admin.repository.UserManagerRepository;
import spring.security.conquer.admin.service.UserManagementService;
import spring.security.conquer.domain.dto.AccountDto;
import spring.security.conquer.domain.entity.Account;
import spring.security.conquer.domain.entity.Role;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service("userManagementService")
public class UserManagementServiceImpl implements UserManagementService {

    private final UserManagerRepository userManagerRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserManagementServiceImpl(UserManagerRepository userManagerRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userManagerRepository = userManagerRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void modifyUser(AccountDto accountDto) {
        ModelMapper modelMapper = new ModelMapper();
        Account account = modelMapper.map(accountDto, Account.class);

        if (accountDto.getRoles() != null) {
            Set<Role> roles = new HashSet<>();
            accountDto.getRoles().forEach(role -> {
                Role r = roleRepository.findByRoleName(role);
                roles.add(r);
            });
            account.setUserRoles(roles);
        }
        account.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        userManagerRepository.save(account);
    }

    @Override
    @Transactional
    public AccountDto getUser(Long id) {
        Account account = userManagerRepository.findById(id).orElse(new Account());
        ModelMapper modelMapper = new ModelMapper();
        AccountDto accountDto = modelMapper.map(account, AccountDto.class);

        List<String> roles = account.getUserRoles()
                .stream()
                .map(Role::getRoleName)
                .toList();

        accountDto.setRoles(roles);
        return accountDto;
    }

    @Override
    @Transactional
    public List<Account> getUsers() {
        return userManagerRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userManagerRepository.deleteById(id);
    }
}
