package app.service.controllers.admin.users;

import app.domain.entites.users.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountManagementService {

    @Autowired
    private AccountService accountService;

    public ResponseEntity<String> registerNewUser(Account account) {
        if (!accountService.existsByUsername(account.getUsername())) {
            accountService.saveAccount(account);
            return ResponseEntity.ok("Пользователь был успешно добавлен!");
        } else {
            return ResponseEntity.ok("Пользователь с таким именем уже существует!");
        }
    }

    public ResponseEntity<String> editUser(Account account) {
        if (accountService.existsById(account.getId())) {
            accountService.saveAccount(account);
            return ResponseEntity.ok("Данные пользователя обновлены!");
        } else {
            return ResponseEntity.ok("Пользователя с таким ID не существует");
        }
    }

    public List<Account> getAllUsers() {
        return accountService.findAllAccounts();
    }

    public ResponseEntity<String> deleteUserById(Long id) {
        accountService.deleteById(id);
        return ResponseEntity.ok("Пользователь был успешно удален!");
    }
}
