package app.service.controllers.admin.users.roles;

import app.domain.entites.users.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoleManagementServiceTest {

    @Mock
    private RoleService roleService;

    @InjectMocks
    private RoleManagementService roleManagementService;

    @Test
    void getAllRoles_ShouldReturnListOfRoles() {
        // Arrange
        List<Role> roles = List.of(new Role(), new Role());
        when(roleService.findAll()).thenReturn(roles);

        // Act
        List<Role> result = roleManagementService.getAllRoles();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(roleService, times(1)).findAll();
    }

    @Test
    void getRole_ShouldReturnRoleOptional_WhenFound() {
        // Arrange
        Integer roleId = 1;
        Optional<Role> optionalRole = Optional.of(new Role());
        when(roleService.findById(roleId)).thenReturn(optionalRole);

        // Act
        Optional<Role> result = roleManagementService.getRoleById(roleId);

        // Assert
        assertTrue(result.isPresent());
        verify(roleService, times(1)).findById(roleId);
    }

    @Test
    void getRole_ShouldReturnEmptyOptional_WhenNotFound() {
        // Arrange
        Integer roleId = 1;
        when(roleService.findById(roleId)).thenReturn(Optional.empty());

        // Act
        Optional<Role> result = roleManagementService.getRoleById(roleId);

        // Assert
        assertTrue(result.isEmpty());
        verify(roleService, times(1)).findById(roleId);
    }

    @Test
    void addRole_ShouldReturnBadRequest_WhenRoleExists() {
        // Arrange
        Role role = new Role();
        when(roleService.existsByRole(role.getRole())).thenReturn(true);

        // Act
        ResponseEntity<String> response = roleManagementService.addRole(role);

        // Assert
        assertEquals(ResponseEntity.badRequest().body("Роль с таким названием уже существует!"), response);
        verify(roleService, times(1)).existsByRole(role.getRole());
        verify(roleService, never()).save(any());
    }

    @Test
    void addRole_ShouldSaveRole_WhenRoleDoesNotExist() {
        // Arrange
        Role role = new Role();
        when(roleService.existsByRole(role.getRole())).thenReturn(false);

        // Act
        ResponseEntity<String> response = roleManagementService.addRole(role);

        // Assert
        assertEquals(ResponseEntity.ok("Роль была успешно добавлена!"), response);
        verify(roleService, times(1)).existsByRole(role.getRole());
        verify(roleService, times(1)).save(role);
    }

    @Test
    void updateRole_ShouldUpdateRole_WhenRoleExists() {
        // Arrange
        Role role = new Role();
        role.setId(1);
        when(roleService.existsById(role.getId())).thenReturn(true);

        // Act
        ResponseEntity<String> response = roleManagementService.updateRole(role);

        // Assert
        assertEquals(ResponseEntity.ok("Роль успешно обновлена!"), response);
        verify(roleService, times(1)).existsById(role.getId());
        verify(roleService, times(1)).save(role);
    }

    @Test
    void updateRole_ShouldReturnNotFound_WhenRoleDoesNotExist() {
        // Arrange
        Role role = new Role();
        role.setId(1);
        when(roleService.existsById(role.getId())).thenReturn(false);

        // Act
        ResponseEntity<String> response = roleManagementService.updateRole(role);

        // Assert
        assertEquals(ResponseEntity.badRequest().body("Роли с таким ID не существует"), response);
        verify(roleService, times(1)).existsById(role.getId());
        verify(roleService, never()).save(any());
    }

    @Test
    void deleteRole_ShouldCallDeleteMethod() {
        // Arrange
        Integer roleId = 1;

        // Act
        ResponseEntity<String> response = roleManagementService.deleteRole(roleId);

        // Assert
        assertEquals(ResponseEntity.ok("Роль была успешно удалена!"), response);
        verify(roleService, times(1)).deleteById(roleId);
    }
}
