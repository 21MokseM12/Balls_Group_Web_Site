package app.controllers.admin.api.concerts;

import app.domain.entites.concerts.Concert;
import app.service.controllers.admin.concerts.concerts_management.ConcertsService;
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

@WebMvcTest(controllers = ConcertUpdateController.class)
@Import(TestSecurityConfig.class)
public class ConcertUpdateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConcertsService concertsService;

    @Test
    public void testUpdateConcert_Success() throws Exception {
        // Мокируем сервис, чтобы метод updateConcert возвращал успешный ответ
        when(concertsService.updateConcert(any(Concert.class)))
                .thenReturn(ResponseEntity.ok("Concert updated successfully"));

        // Выполняем PUT-запрос и проверяем статус ответа и его содержимое
        mockMvc.perform(put("/api/v1/edit-concerts/update/concert/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                  {
                                      "id": 1,
                                      "city": "Moscow 1",
                                      "description": "Updated Concert",
                                      "concertVenue": "Updated Venue",
                                      "date": "2024-12-30",
                                      "ticketsLink": "updatedTicketLink",
                                      "meetingLink": "updatedMeetingLink"
                                  }
                             """))
                .andExpect(status().isOk())  // Проверка статус-кода 200 OK
                .andExpect(content().string("Concert updated successfully"));  // Проверка содержимого тела ответа
    }

    @Test
    public void testUpdateConcert_Failure() throws Exception {
        // Мокируем сервис, чтобы метод updateConcert возвращал ошибку
        when(concertsService.updateConcert(any(Concert.class)))
                .thenReturn(new ResponseEntity<>("Error updating concert", HttpStatus.BAD_REQUEST));

        // Выполняем PUT-запрос и проверяем статус ответа и его содержимое
        mockMvc.perform(put("/api/v1/edit-concerts/update/concert/")
                        .contentType("application/json")
                        .content("""
                                  {
                                      "id": 1,
                                      "city": "Moscow 1",
                                      "description": "Invalid Concert",
                                      "concertVenue": "Invalid Venue",
                                      "date": "2024-12-30",
                                      "ticketsLink": "invalidTicketLink",
                                      "meetingLink": "invalidMeetingLink"
                                  }
                             """))
                .andExpect(status().isBadRequest())  // Проверка статус-кода 400 Bad Request
                .andExpect(content().string("Error updating concert"));  // Проверка содержимого тела ответа
    }
}
