package app.controllers.admin.api.users;

import app.service.controllers.admin.users.users_management.UsersService;
import config.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UsersDeleteController.class)
@Import(TestSecurityConfig.class)
public class UsersDeleteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsersService usersService; // Мокаем интерфейс

    @Test
    public void testDeleteAccountSuccess() throws Exception {
        Long accountID = 1L;

        // Задаем поведение мока для интерфейса
        when(usersService.deleteAccount(accountID)).thenReturn(ResponseEntity.status(HttpStatus.OK).body("Account deleted"));

        mockMvc.perform(delete("/api/v1/edit-users/delete/account/{id}", accountID))
                .andExpect(status().isOk())
                .andExpect(content().string("Account deleted"));

        // Проверяем, что метод был вызван
        verify(usersService, times(1)).deleteAccount(accountID);
    }

    @Test
    public void testDeleteAccountNotFound() throws Exception {
        Long accountID = 2L;

        // Задаем поведение мока для интерфейса
        when(usersService.deleteAccount(accountID)).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found"));

        mockMvc.perform(delete("/api/v1/edit-users/delete/account/{id}", accountID))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Account not found"));

        verify(usersService, times(1)).deleteAccount(accountID);
    }

    @Test
    public void testDeleteAccountRoleSuccess() throws Exception {
        Integer roleID = 1;

        // Задаем поведение мока для интерфейса
        when(usersService.deleteRole(roleID)).thenReturn(ResponseEntity.status(HttpStatus.OK).body("Role deleted"));

        mockMvc.perform(delete("/api/v1/edit-users/delete/role/{id}", roleID))
                .andExpect(status().isOk())
                .andExpect(content().string("Role deleted"));

        // Проверяем, что метод был вызван
        verify(usersService, times(1)).deleteRole(roleID);
    }

    @Test
    public void testDeleteAccountRoleNotFound() throws Exception {
        Integer roleID = 2;

        // Задаем поведение мока для интерфейса
        when(usersService.deleteRole(roleID)).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Role not found"));

        mockMvc.perform(delete("/api/v1/edit-users/delete/role/{id}", roleID))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Role not found"));

        verify(usersService, times(1)).deleteRole(roleID);
    }
}

