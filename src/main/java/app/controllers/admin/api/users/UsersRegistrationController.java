package app.controllers.admin.api.users;

import app.domain.entites.users.Account;
import app.domain.entites.users.Role;
import app.service.controllers.admin.users.users_management.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/edit-users/add/")
public class UsersRegistrationController {

    @Autowired
    private UsersService usersService;

    @PostMapping("account/")
    public ResponseEntity<String> addAccount(@RequestBody Account account) {
        return usersService.addAccount(account);
    }

    @PostMapping("role/")
    public ResponseEntity<String> addRole(@RequestBody Role role) {
        return usersService.addRole(role);
    }
}
