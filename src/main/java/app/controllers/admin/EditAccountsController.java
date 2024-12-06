package app.controllers.admin;

import app.domain.users.Account;
import app.service.admin.AccountEditAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/edit-users/")
public class EditAccountsController {

    @Autowired
    private AccountEditAdminService accountService;

    @PostMapping("register/")
    @ResponseBody
    public ResponseEntity<String> registerNewUser(@RequestBody Account account) {
        if (!accountService.existsByUsername(account.getUsername())) {
            accountService.encodeAccountPassword(account);
            accountService.saveAccount(account);
            return ResponseEntity.ok("Пользователь был успешно добавлен!");
        } else {
            return ResponseEntity.ok("Пользователь с таким именем уже существует!");
        }
    }

    @PutMapping("edit/")
    @ResponseBody
    public ResponseEntity<String> editUser(@RequestBody Account account) {
        if (accountService.existsById(account.getId())) {
            accountService.encodeAccountPassword(account);
            accountService.saveAccount(account);
            return ResponseEntity.ok("Данные пользователя обновлены!");
        } else {
            return ResponseEntity.ok("Пользователя с таким ID не существует");
        }
    }

    @GetMapping("get-all/")
    public List<Account> getAllUsers() {
        return accountService.findAllAccounts();
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        accountService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
