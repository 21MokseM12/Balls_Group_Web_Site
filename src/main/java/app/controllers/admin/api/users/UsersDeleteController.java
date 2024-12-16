package app.controllers.admin.api.users;

import app.service.controllers.admin.users.users_management.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/edit-users/delete/")
public class UsersDeleteController {

    @Autowired
    private UsersService usersService;

    @DeleteMapping("account/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        return usersService.deleteAccount(id);
    }

    @DeleteMapping("role/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable Integer id) {
        return usersService.deleteRole(id);
    }
}
