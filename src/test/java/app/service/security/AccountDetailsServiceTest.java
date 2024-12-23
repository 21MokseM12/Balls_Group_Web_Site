package app.service.security;

import app.domain.entites.users.Account;
import app.repository.users.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountDetailsServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountDetailsService accountDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername_UserExists() {
        // Arrange
        String username = "testUser";
        Account account = new Account();
        account.setUsername(username);
        account.setPassword("testPassword");
        when(accountRepository.findAccountByUsername(username)).thenReturn(Optional.of(account));

        // Act
        UserDetails userDetails = accountDetailsService.loadUserByUsername(username);

        // Assert
        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        assertEquals("testPassword", userDetails.getPassword());
        verify(accountRepository, times(1)).findAccountByUsername(username);
    }

    @Test
    void loadUserByUsername_UserNotFound() {
        // Arrange
        String username = "nonExistentUser";
        when(accountRepository.findAccountByUsername(username)).thenReturn(Optional.empty());

        // Act & Assert
        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> accountDetailsService.loadUserByUsername(username)
        );
        assertEquals("account nonExistentUser not found", exception.getMessage());
        verify(accountRepository, times(1)).findAccountByUsername(username);
    }
}
