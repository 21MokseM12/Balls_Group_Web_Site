package app.service.controllers.admin.users.accounts;

import app.domain.entites.users.Account;
import app.service.controllers.admin.users.roles.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountManagementService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private RoleService roleService;

    public List<Account> getAllAccounts() {
        return accountService.findAll();
    }

    public Optional<Account> getAccountById(Long id) {
        return accountService.findById(id);
    }

    public ResponseEntity<String> addAccount(Account account) {
        if (!accountService.existsByUsername(account.getUsername())) {
            setAccountsRoles(account);
            accountService.save(account);
            return ResponseEntity.ok("Пользователь был успешно добавлен!");
        } else {
            return ResponseEntity.ok("Пользователь с таким именем уже существует!");
        }
    }

    public ResponseEntity<String> updateAccount(Account account) {
        if (accountService.existsById(account.getId())) {
            setAccountsRoles(account);
            accountService.save(account);
            return ResponseEntity.ok("Данные пользователя обновлены!");
        } else {
            return ResponseEntity.ok("Пользователя с таким ID не существует");
        }
    }

    public ResponseEntity<String> deleteAccount(Long id) {
        accountService.deleteById(id);
        return ResponseEntity.ok("Пользователь был успешно удален!");
    }

    private void setAccountsRoles(Account account) {
        account.setRoles(
                account.getRoles().stream()
                        .map(role -> roleService.findByRole(role.getRole()))
                        .collect(Collectors.toSet())
        );
    }
}
