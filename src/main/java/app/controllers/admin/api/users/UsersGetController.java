package app.controllers.admin.api.users;

import app.domain.entites.users.Account;
import app.domain.entites.users.Role;
import app.service.controllers.admin.users.users_management.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/edit-users/get/")
public class UsersGetController {

    @Autowired
    private UsersService usersService;

    @GetMapping("account/{id}")
    public Optional<Account> getAccount(@PathVariable Long id) {
        return usersService.getAccount(id);
    }

    @GetMapping("role/{id}")
    public Optional<Role> getRole(@PathVariable Integer id) {
        return usersService.getRole(id);
    }
}
