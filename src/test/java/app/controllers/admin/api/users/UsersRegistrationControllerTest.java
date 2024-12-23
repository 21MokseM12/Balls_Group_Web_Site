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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UsersRegistrationController.class)
@Import(TestSecurityConfig.class)
public class UsersRegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsersService usersService;

    @Test
    public void testAddAccount_Success() throws Exception {
        // Мокируем сервис, чтобы метод addConcert возвращал успешный ответ
        when(usersService.addAccount(any(Account.class)))
                .thenReturn(ResponseEntity.ok("Account added successfully"));

        // Выполняем POST-запрос и проверяем статус ответа
        mockMvc.perform(post("/api/v1/edit-users/add/account/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
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
                                 """))
                .andExpect(status().isOk())
                .andExpect(content().string("Account added successfully"));

        // Проверяем, что метод был вызван
        verify(usersService, times(1)).addAccount(any(Account.class));
    }

    @Test
    public void testAddAccount_Failure() throws Exception {
        // Мокируем сервис, чтобы метод addConcert возвращал ошибку
        when(usersService.addAccount(any(Account.class)))
                .thenReturn(new ResponseEntity<>("Error adding account", HttpStatus.BAD_REQUEST));

        // Выполняем POST-запрос и проверяем статус ответа
        mockMvc.perform(post("/api/v1/edit-users/add/account/")
                        .contentType("application/json")
                        .content("{\"name\": \"Invalid Concert\", \"date\": \"2024-12-25\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error adding account"));
    }

    @Test
    public void testAddAccountRole_Success() throws Exception {
        // Мокируем сервис, чтобы метод addConcert возвращал успешный ответ
        when(usersService.addRole(any(Role.class)))
                .thenReturn(ResponseEntity.ok("Role added successfully"));

        // Выполняем POST-запрос и проверяем статус ответа
        mockMvc.perform(post("/api/v1/edit-users/add/role/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                 {
                                   "id": 1,
                                   "role": "Role 1"
                                 }
                                 """))
                .andExpect(status().isOk())
                .andExpect(content().string("Role added successfully"));

        // Проверяем, что метод был вызван
        verify(usersService, times(1)).addRole(any(Role.class));
    }

    @Test
    public void testAddAccountRole_Failure() throws Exception {
        // Мокируем сервис, чтобы метод addConcert возвращал ошибку
        when(usersService.addRole(any(Role.class)))
                .thenReturn(new ResponseEntity<>("Error adding role", HttpStatus.BAD_REQUEST));

        // Выполняем POST-запрос и проверяем статус ответа
        mockMvc.perform(post("/api/v1/edit-users/add/role/")
                        .contentType("application/json")
                        .content("{\"name\": \"Invalid Concert\", \"date\": \"2024-12-25\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error adding role"));
    }
}
