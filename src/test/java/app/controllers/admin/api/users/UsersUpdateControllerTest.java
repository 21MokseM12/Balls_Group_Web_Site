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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UsersUpdateController.class)
@Import(TestSecurityConfig.class)
public class UsersUpdateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsersService usersService;

    @Test
    public void testUpdateAccount_Success() throws Exception {
        // Мокируем сервис, чтобы метод updateConcert возвращал успешный ответ
        when(usersService.updateAccount(any(Account.class)))
                .thenReturn(ResponseEntity.ok("Account updated successfully"));

        // Выполняем PUT-запрос и проверяем статус ответа и его содержимое
        mockMvc.perform(put("/api/v1/edit-users/update/account/")
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
                .andExpect(status().isOk())  // Проверка статус-кода 200 OK
                .andExpect(content().string("Account updated successfully"));  // Проверка содержимого тела ответа
    }

    @Test
    public void testUpdateAccount_Failure() throws Exception {
        // Мокируем сервис, чтобы метод updateConcert возвращал ошибку
        when(usersService.updateAccount(any(Account.class)))
                .thenReturn(new ResponseEntity<>("Error updating account", HttpStatus.BAD_REQUEST));

        // Выполняем PUT-запрос и проверяем статус ответа и его содержимое
        mockMvc.perform(put("/api/v1/edit-users/update/account/")
                        .contentType("application/json")
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
                .andExpect(status().isBadRequest())  // Проверка статус-кода 400 Bad Request
                .andExpect(content().string("Error updating account"));  // Проверка содержимого тела ответа
    }

    @Test
    public void testUpdateAccountRole_Success() throws Exception {
        // Мокируем сервис, чтобы метод updateConcert возвращал успешный ответ
        when(usersService.updateRole(any(Role.class)))
                .thenReturn(ResponseEntity.ok("Role updated successfully"));

        // Выполняем PUT-запрос и проверяем статус ответа и его содержимое
        mockMvc.perform(put("/api/v1/edit-users/update/role/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                  {
                                      "id": 1,
                                      "role": "Role 1"
                                  }
                             """))
                .andExpect(status().isOk())  // Проверка статус-кода 200 OK
                .andExpect(content().string("Role updated successfully"));  // Проверка содержимого тела ответа
    }

    @Test
    public void testUpdateAccountRole_Failure() throws Exception {
        // Мокируем сервис, чтобы метод updateConcert возвращал ошибку
        when(usersService.updateRole(any(Role.class)))
                .thenReturn(new ResponseEntity<>("Error updating role", HttpStatus.BAD_REQUEST));

        // Выполняем PUT-запрос и проверяем статус ответа и его содержимое
        mockMvc.perform(put("/api/v1/edit-users/update/role/")
                        .contentType("application/json")
                        .content("""
                                  {
                                      "id": 1,
                                      "role": "Role 1"
                                  }
                             """))
                .andExpect(status().isBadRequest())  // Проверка статус-кода 400 Bad Request
                .andExpect(content().string("Error updating role"));  // Проверка содержимого тела ответа
    }
}
