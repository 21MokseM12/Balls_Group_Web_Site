package app.controllers.admin.api.users;

import app.domain.entites.users.Account;
import app.domain.entites.users.Role;
import app.service.controllers.admin.users.users_management.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/edit-users/update/")
public class UsersUpdateController {

    @Autowired
    private UsersService usersService;

    @PutMapping("account/")
    public ResponseEntity<String> updateAccount(@RequestBody Account account) {
        return usersService.updateAccount(account);
    }

    @PutMapping("role/")
    public ResponseEntity<String> updateRole(@RequestBody Role role) {
        return usersService.updateRole(role);
    }
}
