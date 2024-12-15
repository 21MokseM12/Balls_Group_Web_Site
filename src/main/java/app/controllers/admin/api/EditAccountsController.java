package app.controllers.admin.api;

import app.domain.entites.users.Account;
import app.service.controllers.admin.users.AccountManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/edit-users/")
public class EditAccountsController {

    @Autowired
    private AccountManagementService accountService;

    @PostMapping("register/")
    public ResponseEntity<String> registerNewUser(@RequestBody Account account) {
        return accountService.registerNewUser(account);
    }

    @PutMapping("edit/")
    public ResponseEntity<String> editUser(@RequestBody Account account) {
        return accountService.editUser(account);
    }

    @GetMapping("get-all/")
    public List<Account> getAllUsers() {
        return accountService.getAllUsers();
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        return accountService.deleteUserById(id);
    }
}
