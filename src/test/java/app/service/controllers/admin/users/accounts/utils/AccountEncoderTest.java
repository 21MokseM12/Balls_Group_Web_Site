package app.service.controllers.admin.users.accounts.utils;

import app.domain.entites.users.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountEncoderTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AccountEncoder accountEncoder;

    @Test
    void encodePassword_ShouldEncodeAndSetPassword() {
        // Arrange
        Account account = new Account();
        account.setPassword("rawPassword");

        String encodedPassword = "encodedPassword";
        when(passwordEncoder.encode("rawPassword")).thenReturn(encodedPassword);

        // Act
        accountEncoder.encodePassword(account);

        // Assert
        assertEquals(encodedPassword, account.getPassword());
        verify(passwordEncoder, times(1)).encode("rawPassword");
    }
}
