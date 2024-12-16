package app.service.controllers.admin.users.users_management;

import app.domain.entites.users.Account;
import app.domain.entites.users.Role;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UsersService {
    List<Account> getAllAccounts();

    Optional<Account> getAccountById(Long id);

    ResponseEntity<String> addAccount(Account account);

    ResponseEntity<String> updateAccount(Account account);

    ResponseEntity<String> deleteAccount(Long id);

    List<Role> getAllRoles();

    Optional<Role> getRoleById(Integer id);

    ResponseEntity<String> addRole(Role role);

    ResponseEntity<String> updateRole(Role role);

    ResponseEntity<String> deleteRole(Integer id);
}
