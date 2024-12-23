package app.service.controllers.admin.users.accounts;

import app.domain.entites.users.Account;
import app.domain.entites.users.Role;
import app.service.controllers.admin.users.roles.RoleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountManagementServiceTest {

    @Mock
    private AccountService accountService;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private AccountManagementService accountManagementService;

    @Test
    void getAllAccounts_ShouldReturnListOfAccounts() {
        // Arrange
        List<Account> mockAccounts = List.of(new Account(), new Account());
        when(accountService.findAll()).thenReturn(mockAccounts);

        // Act
        List<Account> result = accountManagementService.getAllAccounts();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(accountService, times(1)).findAll();
    }

    @Test
    void getAccountById_ShouldReturnAccountOptional_WhenFound() {
        // Arrange
        Long accountId = 1L;
        Account mockAccount = new Account();
        when(accountService.findById(accountId)).thenReturn(Optional.of(mockAccount));

        // Act
        Optional<Account> result = accountManagementService.getAccountById(accountId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(mockAccount, result.get());
        verify(accountService, times(1)).findById(accountId);
    }

    @Test
    void getAccountById_ShouldReturnEmptyOptional_WhenNotFound() {
        // Arrange
        Long accountId = 1L;
        when(accountService.findById(accountId)).thenReturn(Optional.empty());

        // Act
        Optional<Account> result = accountManagementService.getAccountById(accountId);

        // Assert
        assertTrue(result.isEmpty());
        verify(accountService, times(1)).findById(accountId);
    }

    @Test
    void addAccount_ShouldAddAccount_WhenAccountDoesNotExist() {
        // Arrange
        Account account = new Account();
        account.setUsername("testUser");
        account.setRoles(Set.of(new Role("USER")));

        when(accountService.existsByUsername(account.getUsername())).thenReturn(false);
        when(roleService.findByRole("USER")).thenReturn(new Role("USER"));

        // Act
        ResponseEntity<String> response = accountManagementService.addAccount(account);

        // Assert
        assertEquals("Пользователь был успешно добавлен!", response.getBody());
        verify(accountService, times(1)).save(account);
    }

    @Test
    void addAccount_ShouldNotAddAccount_WhenAccountAlreadyExists() {
        // Arrange
        Account account = new Account();
        account.setUsername("testUser");

        when(accountService.existsByUsername(account.getUsername())).thenReturn(true);

        // Act
        ResponseEntity<String> response = accountManagementService.addAccount(account);

        // Assert
        assertEquals("Пользователь с таким именем уже существует!", response.getBody());
        verify(accountService, never()).save(account);
    }

    @Test
    void updateAccount_ShouldUpdateAccount_WhenAccountExists() {
        // Arrange
        Account account = new Account();
        account.setId(1L);
        account.setRoles(Set.of(new Role("ADMIN")));

        when(accountService.existsById(account.getId())).thenReturn(true);
        when(roleService.findByRole("ADMIN")).thenReturn(new Role("ADMIN"));

        // Act
        ResponseEntity<String> response = accountManagementService.updateAccount(account);

        // Assert
        assertEquals("Данные пользователя обновлены!", response.getBody());
        verify(accountService, times(1)).save(account);
    }

    @Test
    void updateAccount_ShouldNotUpdateAccount_WhenAccountDoesNotExist() {
        // Arrange
        Account account = new Account();
        account.setId(1L);

        when(accountService.existsById(account.getId())).thenReturn(false);

        // Act
        ResponseEntity<String> response = accountManagementService.updateAccount(account);

        // Assert
        assertEquals("Пользователя с таким ID не существует", response.getBody());
        verify(accountService, never()).save(account);
    }

    @Test
    void deleteAccount_ShouldDeleteAccount_WhenCalled() {
        // Arrange
        Long accountId = 1L;

        // Act
        ResponseEntity<String> response = accountManagementService.deleteAccount(accountId);

        // Assert
        assertEquals("Пользователь был успешно удален!", response.getBody());
        verify(accountService, times(1)).deleteById(accountId);
    }

    @Test
    void setAccountsRoles_ShouldSetCorrectRoles() {
        // Arrange
        Account account = new Account();
        account.setRoles(Set.of(new Role("USER"), new Role("ADMIN")));

        when(roleService.findByRole("USER")).thenReturn(new Role("USER"));
        when(roleService.findByRole("ADMIN")).thenReturn(new Role("ADMIN"));

        // Act
        accountManagementService.addAccount(account);

        // Assert
        assertEquals(2, account.getRoles().size());
        verify(roleService, times(1)).findByRole("USER");
        verify(roleService, times(1)).findByRole("ADMIN");
    }
}

