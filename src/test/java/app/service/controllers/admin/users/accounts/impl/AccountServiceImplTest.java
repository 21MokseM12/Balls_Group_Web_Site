package app.service.controllers.admin.users.accounts.impl;

import app.domain.entites.users.Account;
import app.repository.users.AccountRepository;
import app.service.controllers.admin.users.accounts.utils.AccountEncoder;
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
public class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountEncoder accountEncoder;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void findAll_ShouldReturnAllAccounts() {
        // Arrange
        List<Account> mockAccounts = List.of(new Account(), new Account());
        when(accountRepository.findAll()).thenReturn(mockAccounts);

        // Act
        List<Account> result = accountService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(accountRepository, times(1)).findAll();
    }

    @Test
    void findById_ShouldReturnAccount_WhenFound() {
        // Arrange
        Long accountId = 1L;
        Account mockAccount = new Account();
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(mockAccount));

        // Act
        Optional<Account> result = accountService.findById(accountId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(mockAccount, result.get());
        verify(accountRepository, times(1)).findById(accountId);
    }

    @Test
    void findById_ShouldReturnEmptyOptional_WhenNotFound() {
        // Arrange
        Long accountId = 1L;
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        // Act
        Optional<Account> result = accountService.findById(accountId);

        // Assert
        assertTrue(result.isEmpty());
        verify(accountRepository, times(1)).findById(accountId);
    }

    @Test
    void save_ShouldEncodePasswordAndSaveAccount() {
        // Arrange
        Account account = new Account();
        account.setPassword("rawPassword");

        // Act
        accountService.save(account);

        // Assert
        verify(accountEncoder, times(1)).encodePassword(account);
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void existsByUsername_ShouldReturnTrue_WhenUsernameExists() {
        // Arrange
        String username = "testUser";
        when(accountRepository.existsByUsername(username)).thenReturn(true);

        // Act
        boolean exists = accountService.existsByUsername(username);

        // Assert
        assertTrue(exists);
        verify(accountRepository, times(1)).existsByUsername(username);
    }

    @Test
    void existsByUsername_ShouldReturnFalse_WhenUsernameDoesNotExist() {
        // Arrange
        String username = "testUser";
        when(accountRepository.existsByUsername(username)).thenReturn(false);

        // Act
        boolean exists = accountService.existsByUsername(username);

        // Assert
        assertFalse(exists);
        verify(accountRepository, times(1)).existsByUsername(username);
    }

    @Test
    void existsById_ShouldReturnTrue_WhenIdExists() {
        // Arrange
        Long id = 1L;
        when(accountRepository.existsById(id)).thenReturn(true);

        // Act
        boolean exists = accountService.existsById(id);

        // Assert
        assertTrue(exists);
        verify(accountRepository, times(1)).existsById(id);
    }

    @Test
    void existsById_ShouldReturnFalse_WhenIdDoesNotExist() {
        // Arrange
        Long id = 1L;
        when(accountRepository.existsById(id)).thenReturn(false);

        // Act
        boolean exists = accountService.existsById(id);

        // Assert
        assertFalse(exists);
        verify(accountRepository, times(1)).existsById(id);
    }

    @Test
    void deleteById_ShouldDeleteAccount_WhenCalled() {
        // Arrange
        Long id = 1L;

        // Act
        accountService.deleteById(id);

        // Assert
        verify(accountRepository, times(1)).deleteById(id);
    }
}
