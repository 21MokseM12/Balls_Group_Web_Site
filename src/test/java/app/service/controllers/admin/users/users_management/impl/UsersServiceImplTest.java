package app.service.controllers.admin.users.users_management.impl;

import app.domain.entites.users.Account;
import app.domain.entites.users.Role;
import app.service.controllers.admin.users.accounts.AccountManagementService;
import app.service.controllers.admin.users.roles.RoleManagementService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsersServiceImplTest {

    @Mock
    private AccountManagementService accountService;

    @Mock
    private RoleManagementService roleService;

    @InjectMocks
    private UsersServiceImpl usersService;

    @Test
    void testGetAllAccounts() {
        Account account1 = new Account();
        Account account2 = new Account();
        when(accountService.getAllAccounts()).thenReturn(Arrays.asList(account1, account2));

        var accounts = usersService.getAllAccounts();

        assertNotNull(accounts);
        assertEquals(2, accounts.size());
        verify(accountService, times(1)).getAllAccounts();
    }

    @Test
    void testGetAccount() {
        Account account = new Account();
        when(accountService.getAccountById(1L)).thenReturn(Optional.of(account));

        Optional<Account> foundAccount = usersService.getAccount(1L);

        assertTrue(foundAccount.isPresent());
        assertEquals(account, foundAccount.get());
        verify(accountService, times(1)).getAccountById(1L);
    }

    @Test
    void testAddAccount() {
        Account account = new Account();
        when(accountService.addAccount(account)).thenReturn(ResponseEntity.ok("Account added"));

        ResponseEntity<String> response = usersService.addAccount(account);

        assertEquals("Account added", response.getBody());
        verify(accountService, times(1)).addAccount(account);
    }

    @Test
    void testUpdateAccount() {
        Account account = new Account();
        when(accountService.updateAccount(account)).thenReturn(ResponseEntity.ok("Account updated"));

        ResponseEntity<String> response = usersService.updateAccount(account);

        assertEquals("Account updated", response.getBody());
        verify(accountService, times(1)).updateAccount(account);
    }

    @Test
    void testDeleteAccount() {
        when(accountService.deleteAccount(1L)).thenReturn(ResponseEntity.ok("Account deleted"));

        ResponseEntity<String> response = usersService.deleteAccount(1L);

        assertEquals("Account deleted", response.getBody());
        verify(accountService, times(1)).deleteAccount(1L);
    }

    @Test
    void testGetAllRoles() {
        Role role1 = new Role();
        Role role2 = new Role();
        when(roleService.getAllRoles()).thenReturn(Arrays.asList(role1, role2));

        var roles = usersService.getAllRoles();

        assertNotNull(roles);
        assertEquals(2, roles.size());
        verify(roleService, times(1)).getAllRoles();
    }

    @Test
    void testGetRole() {
        Role role = new Role();
        when(roleService.getRoleById(1)).thenReturn(Optional.of(role));

        Optional<Role> foundRole = usersService.getRole(1);

        assertTrue(foundRole.isPresent());
        assertEquals(role, foundRole.get());
        verify(roleService, times(1)).getRoleById(1);
    }

    @Test
    void testAddRole() {
        Role role = new Role();
        when(roleService.addRole(role)).thenReturn(ResponseEntity.ok("Role added"));

        ResponseEntity<String> response = usersService.addRole(role);

        assertEquals("Role added", response.getBody());
        verify(roleService, times(1)).addRole(role);
    }

    @Test
    void testUpdateRole() {
        Role role = new Role();
        when(roleService.updateRole(role)).thenReturn(ResponseEntity.ok("Role updated"));

        ResponseEntity<String> response = usersService.updateRole(role);

        assertEquals("Role updated", response.getBody());
        verify(roleService, times(1)).updateRole(role);
    }

    @Test
    void testDeleteRole() {
        when(roleService.deleteRole(1)).thenReturn(ResponseEntity.ok("Role deleted"));

        ResponseEntity<String> response = usersService.deleteRole(1);

        assertEquals("Role deleted", response.getBody());
        verify(roleService, times(1)).deleteRole(1);
    }
}
