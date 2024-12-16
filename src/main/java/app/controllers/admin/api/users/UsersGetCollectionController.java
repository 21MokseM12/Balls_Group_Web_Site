package app.controllers.admin.api.users;

import app.domain.entites.users.Account;
import app.domain.entites.users.Role;
import app.service.controllers.admin.users.users_management.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/edit-users/get-all/")
public class UsersGetCollectionController {

    @Autowired
    private UsersService usersService;

    @GetMapping("accounts/")
    public List<Account> getAllAccounts() {
        return usersService.getAllAccounts();
    }

    @GetMapping("roles/")
    public List<Role> getAllRoles() {
        return usersService.getAllRoles();
    }
}
