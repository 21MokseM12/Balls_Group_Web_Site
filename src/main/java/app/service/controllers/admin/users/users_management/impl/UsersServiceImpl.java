package app.service.controllers.admin.users.users_management.impl;

import app.domain.entites.users.Account;
import app.domain.entites.users.Role;
import app.service.controllers.admin.users.accounts.AccountManagementService;
import app.service.controllers.admin.users.roles.RoleManagementService;
import app.service.controllers.admin.users.users_management.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private AccountManagementService accountService;

    @Autowired
    private RoleManagementService roleService;

    @Override
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @Override
    public Optional<Account> getAccount(Long id) {
        return accountService.getAccountById(id);
    }

    @Override
    public ResponseEntity<String> addAccount(Account account) {
        return accountService.addAccount(account);
    }

    @Override
    public ResponseEntity<String> updateAccount(Account account) {
        return accountService.updateAccount(account);
    }

    @Override
    public ResponseEntity<String> deleteAccount(Long id) {
        return accountService.deleteAccount(id);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @Override
    public Optional<Role> getRole(Integer id) {
        return roleService.getRoleById(id);
    }

    @Override
    public ResponseEntity<String> addRole(Role role) {
        return roleService.addRole(role);
    }

    @Override
    public ResponseEntity<String> updateRole(Role role) {
        return roleService.updateRole(role);
    }

    @Override
    public ResponseEntity<String> deleteRole(Integer id) {
        return roleService.deleteRole(id);
    }
}
