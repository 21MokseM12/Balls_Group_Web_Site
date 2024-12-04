package app.controllers.admin;

import app.domain.users.Account;
import app.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/add-user/")
public class EditUsersController {

    @Autowired
    private AccountRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("register/")
    @ResponseBody
    public ResponseEntity<String> registerNewUser(@RequestBody Account account) {
        if (!repository.existsByUsername(account.getUsername())) {
            account.setPassword(encoder.encode(account.getPassword()));
            repository.save(account);
            return ResponseEntity.ok("Пользователь был успешно добавлен!");
        } else {
            return ResponseEntity.ok("Пользователь с таким именем уже существует!");
        }
    }

    @PutMapping("edit/")
    @ResponseBody
    public ResponseEntity<String> editUser(@RequestBody Account account) {
        if (repository.existsById(account.getId())) {
            account.setPassword(encoder.encode(account.getPassword()));
            repository.save(account);
            return ResponseEntity.ok("Данные пользователя обновлены!");
        } else {
            return ResponseEntity.ok("Пользователя с таким ID не существует");
        }
    }
}
