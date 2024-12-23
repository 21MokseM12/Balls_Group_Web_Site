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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ConcertPostController.class)
@Import(TestSecurityConfig.class)
public class ConcertPostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConcertsService concertsService;

    @Test
    public void testAddConcert_Success() throws Exception {
        // Мокируем сервис, чтобы метод addConcert возвращал успешный ответ
        when(concertsService.addConcert(any(Concert.class)))
                .thenReturn(ResponseEntity.ok("Concert added successfully"));

        // Выполняем POST-запрос и проверяем статус ответа
        mockMvc.perform(post("/api/v1/edit-concerts/add/concert/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                 {
                                   "id": 1,
                                   "city": "Moscow 1",
                                   "description": "Concert 1",
                                   "concertVenue": "ConcertVenue 1",
                                   "date": "2024-12-30",
                                   "ticketsLink": "ticketLink 1",
                                   "meetingLink": "meetingLink 1"
                                 }
                                 """))
                .andExpect(status().isOk())
                .andExpect(content().string("Concert added successfully"));

        // Проверяем, что метод был вызван
        verify(concertsService, times(1)).addConcert(any(Concert.class));
    }

    @Test
    public void testAddConcert_Failure() throws Exception {
        // Мокируем сервис, чтобы метод addConcert возвращал ошибку
        when(concertsService.addConcert(any(Concert.class)))
                .thenReturn(new ResponseEntity<>("Error adding concert", HttpStatus.BAD_REQUEST));

        // Выполняем POST-запрос и проверяем статус ответа
        mockMvc.perform(post("/api/v1/edit-concerts/add/concert/")
                        .contentType("application/json")
                        .content("{\"name\": \"Invalid Concert\", \"date\": \"2024-12-25\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error adding concert"));
    }
}
