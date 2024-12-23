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

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UsersGetCollectionController.class)
@Import(TestSecurityConfig.class)
public class UsersGetCollectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsersService usersService;

    @Test
    public void testGetCollectionAccountSuccess() throws Exception {
        Account account1 = new Account(1L, "Name 1", "Password 1", Set.of(new Role(1, "ADMIN 1")));
        Account account2 = new Account(2L, "Name 2", "Password 2", Set.of(new Role(2, "ADMIN 2")));
        List<Account> accounts = Arrays.asList(account1, account2);

        // Задаем поведение мока для интерфейса
        when(usersService.getAllAccounts()).thenReturn(accounts);

        mockMvc.perform(get("/api/v1/edit-users/get-all/accounts/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(
                      """
                                [
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
                                  },
                                  {
                                  "id": 2,
                                  "username": "Name 2",
                                  "password": "Password 2",
                                  "roles": [
                                              {
                                                "id": 2,
                                                "role": "ADMIN 2"
                                              }
                                           ]
                                  }
                                ]
                                """));

        // Проверяем, что метод был вызван
        verify(usersService, times(1)).getAllAccounts();
    }

    @Test
    public void testGetCollectionAccountNotFound() throws Exception {
        // Задаем поведение мока
        when(usersService.getAllAccounts()).thenReturn(List.of());

        // Выполняем GET-запрос и проверяем результат
        mockMvc.perform(get("/api/v1/edit-users/get-all/accounts/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void testGetCollectionAccountRoleSuccess() throws Exception {
        Role role1 = new Role(1, "Role 1");
        Role role2 = new Role(2, "Role 2");
        List<Role> roles = Arrays.asList(role1, role2);

        // Задаем поведение мока для интерфейса
        when(usersService.getAllRoles()).thenReturn(roles);

        mockMvc.perform(get("/api/v1/edit-users/get-all/roles/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        """
                                  [
                                    {
                                    "id": 1,
                                    "role": "Role 1"
                                    },
                                    {
                                    "id": 2,
                                    "role": "Role 2"
                                    }
                                  ]
                                  """));

        // Проверяем, что метод был вызван
        verify(usersService, times(1)).getAllRoles();
    }

    @Test
    public void testGetCollectionAccountRoleNotFound() throws Exception {
        // Задаем поведение мока
        when(usersService.getAllRoles()).thenReturn(List.of());

        // Выполняем GET-запрос и проверяем результат
        mockMvc.perform(get("/api/v1/edit-users/get-all/roles/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
