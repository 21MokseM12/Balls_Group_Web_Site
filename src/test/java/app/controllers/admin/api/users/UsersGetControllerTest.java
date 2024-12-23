package app.controllers.admin.api.users;

import app.domain.entites.users.Account;
import app.domain.entites.users.Role;
import app.service.controllers.admin.users.users_management.UsersService;
import config.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UsersGetController.class)
@Import(TestSecurityConfig.class)
public class UsersGetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsersService usersService;

    @Test
    void shouldReturnAccountWhenExists() throws Exception {
        // Подготовка тестовых данных
        Long accountID = 1L;
        Account account1 = new Account(1L, "Name 1", "Password 1", Set.of(new Role(1, "ADMIN 1")));
        Optional<Account> optionalAccount = Optional.of(account1);

        // Задаем поведение мока
        when(usersService.getAccount(accountID)).thenReturn(optionalAccount);

        // Выполняем GET-запрос и проверяем результат
        mockMvc.perform(get("/api/v1/edit-users/get/account/{id}", accountID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                    {
                                      "id": 1,
                                      "username": "Name 1",
                                      "password": "Password 1",
                                      "roles": [
                                                  {
                                                    "id": 1,
                                                    "role": "ADMIN 1"
                                                  }
                                               ]
                                    }
                                    """));
        // Проверяем, что метод был вызван
        verify(usersService, times(1)).getAccount(accountID);
    }

    @Test
    void shouldReturnNotFoundWhenAccountDoesNotExist() throws Exception {
        // Подготовка тестовых данных
        Long accountID = 999L;

        // Задаем поведение мока
        when(usersService.getAccount(accountID)).thenReturn(Optional.empty());

        // Выполняем GET-запрос и проверяем результат
        mockMvc.perform(get("/api/v1/edit-users/get/account/{id}", accountID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldHandleInvalidAccountIdFormat() throws Exception {
        // Выполняем GET-запрос с некорректным ID
        mockMvc.perform(get("/api/v1/edit-users/get/account/{id}", "invalidId")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnAccountRoleWhenExists() throws Exception {
        // Подготовка тестовых данных
        Integer roleID = 1;
        Role role1 = new Role(1, "Role 1");
        Optional<Role> optionalRole = Optional.of(role1);

        // Задаем поведение мока
        when(usersService.getRole(roleID)).thenReturn(optionalRole);

        // Выполняем GET-запрос и проверяем результат
        mockMvc.perform(get("/api/v1/edit-users/get/role/{id}", roleID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                    {
                                      "id": 1,
                                      "role": "Role 1"
                                    }
                                    """));
        // Проверяем, что метод был вызван
        verify(usersService, times(1)).getRole(roleID);
    }

    @Test
    void shouldReturnNotFoundWhenAccountRoleDoesNotExist() throws Exception {
        // Подготовка тестовых данных
        Integer roleID = 999;

        // Задаем поведение мока
        when(usersService.getRole(roleID)).thenReturn(Optional.empty());

        // Выполняем GET-запрос и проверяем результат
        mockMvc.perform(get("/api/v1/edit-users/get/role/{id}", roleID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldHandleInvalidRoleIdFormat() throws Exception {
        // Выполняем GET-запрос с некорректным ID
        mockMvc.perform(get("/api/v1/edit-users/get/role/{id}", "invalidId")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
