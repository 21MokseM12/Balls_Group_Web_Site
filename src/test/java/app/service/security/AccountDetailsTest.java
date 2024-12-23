package app.service.security;

import app.domain.entites.users.Account;
import app.domain.entites.users.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class AccountDetailsTest {

    private Account account;
    private AccountDetails accountDetails;

    @BeforeEach
    void setUp() {
        Role role = new Role();
        role.setRole("ROLE_USER");

        account = new Account();
        account.setUsername("testUser");
        account.setPassword("testPassword");
        account.setRoles(Collections.singleton(role));

        accountDetails = new AccountDetails(account);
    }

    @Test
    void testGetAuthorities() {
        // Act
        Set<? extends GrantedAuthority> authorities = (Set<? extends GrantedAuthority>) accountDetails.getAuthorities();

        // Assert
        assertNotNull(authorities);
        assertEquals(1, authorities.size());
        assertTrue(authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
    }

    @Test
    void testGetPassword() {
        // Act
        String password = accountDetails.getPassword();

        // Assert
        assertEquals("testPassword", password);
    }

    @Test
    void testGetUsername() {
        // Act
        String username = accountDetails.getUsername();

        // Assert
        assertEquals("testUser", username);
    }

    @Test
    void testIsAccountNonExpired() {
        // Act
        boolean isNonExpired = accountDetails.isAccountNonExpired();

        // Assert
        assertTrue(isNonExpired);
    }

    @Test
    void testIsAccountNonLocked() {
        // Act
        boolean isNonLocked = accountDetails.isAccountNonLocked();

        // Assert
        assertTrue(isNonLocked);
    }

    @Test
    void testIsCredentialsNonExpired() {
        // Act
        boolean isCredentialsNonExpired = accountDetails.isCredentialsNonExpired();

        // Assert
        assertTrue(isCredentialsNonExpired);
    }

    @Test
    void testIsEnabled() {
        // Act
        boolean isEnabled = accountDetails.isEnabled();

        // Assert
        assertTrue(isEnabled);
    }
}
