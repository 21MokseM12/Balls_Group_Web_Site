package app.service.controllers.admin.users.roles;

import app.domain.entites.users.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleManagementService {

    @Autowired
    private RoleService roleService;

    public List<Role> getAllRoles() {
        return roleService.findAll();
    }

    public Optional<Role> getRoleById(Integer id) {
        return roleService.findById(id);
    }

    public ResponseEntity<String> addRole(Role role) {
        if (!roleService.existsByRole(role.getRole())) {
            roleService.save(role);
            return ResponseEntity.ok("Роль была успешно добавлена!");
        } else {
            return ResponseEntity.ok("Роль с таким названием уже существует!");
        }
    }

    public ResponseEntity<String> updateRole(Role role) {
        if (roleService.existsById(role.getId())) {
            roleService.save(role);
            return ResponseEntity.ok("Роль успешно обновлена!");
        } else {
            return ResponseEntity.ok("Роли с таким ID не существует");
        }
    }

    public ResponseEntity<String> deleteRole(Integer id) {
        roleService.deleteById(id);
        return ResponseEntity.ok("Роль была успешно удалена!");
    }
}
