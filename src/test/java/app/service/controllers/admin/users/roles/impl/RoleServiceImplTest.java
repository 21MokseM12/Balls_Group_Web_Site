package app.service.controllers.admin.users.roles.impl;

import app.domain.entites.users.Role;
import app.repository.users.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    void findAll_ShouldReturnListOfRoles() {
        // Arrange
        List<Role> roles = List.of(new Role(), new Role());
        when(roleRepository.findAll()).thenReturn(roles);

        // Act
        List<Role> result = roleService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(roleRepository, times(1)).findAll();
    }

    @Test
    void findById_ShouldReturnRoleOptional_WhenFound() {
        // Arrange
        Integer roleId = 1;
        Role role = new Role();
        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));

        // Act
        Optional<Role> result = roleService.findById(roleId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(role, result.get());
        verify(roleRepository, times(1)).findById(roleId);
    }

    @Test
    void findById_ShouldReturnEmptyOptional_WhenNotFound() {
        // Arrange
        Integer roleId = 1;
        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        // Act
        Optional<Role> result = roleService.findById(roleId);

        // Assert
        assertTrue(result.isEmpty());
        verify(roleRepository, times(1)).findById(roleId);
    }

    @Test
    void exists_ShouldReturnTrue_WhenRoleExists() {
        // Arrange
        Role role = new Role();
        role.setRole("Role");
        when(roleRepository.existsByRole(role.getRole()))
                .thenReturn(true);

        // Act
        boolean result = roleService.existsByRole(role.getRole());

        // Assert
        assertTrue(result);
        verify(roleRepository, times(1))
                .existsByRole(role.getRole());
    }

    @Test
    void exists_ShouldReturnFalse_WhenRoleDoesNotExist() {
        // Arrange
        Role role = new Role();
        role.setRole("Role");
        when(roleRepository.existsByRole(role.getRole()))
                .thenReturn(false);

        // Act
        boolean result = roleService.existsByRole(role.getRole());

        // Assert
        assertFalse(result);
        verify(roleRepository, times(1))
                .existsByRole(role.getRole());
    }

    @Test
    void save_ShouldSaveRole() {
        // Arrange
        Role role = new Role();

        // Act
        roleService.save(role);

        // Assert
        verify(roleRepository, times(1)).save(role);
    }

    @Test
    void existsById_ShouldReturnTrue_WhenRoleExistsById() {
        // Arrange
        Integer roleId = 1;
        when(roleRepository.existsById(roleId)).thenReturn(true);

        // Act
        boolean result = roleService.existsById(roleId);

        // Assert
        assertTrue(result);
        verify(roleRepository, times(1)).existsById(roleId);
    }

    @Test
    void existsById_ShouldReturnFalse_WhenRoleDoesNotExistById() {
        // Arrange
        Integer roleId = 1;
        when(roleRepository.existsById(roleId)).thenReturn(false);

        // Act
        boolean result = roleService.existsById(roleId);

        // Assert
        assertFalse(result);
        verify(roleRepository, times(1)).existsById(roleId);
    }

    @Test
    void delete_ShouldDeleteRoleById() {
        // Arrange
        Integer roleId = 1;

        // Act
        roleService.deleteById(roleId);

        // Assert
        verify(roleRepository, times(1)).deleteById(roleId);
    }
}
