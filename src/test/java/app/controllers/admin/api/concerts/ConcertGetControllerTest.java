package app.controllers.admin.api.concerts;

import app.domain.entites.concerts.Concert;
import app.service.controllers.admin.concerts.concerts_management.ConcertsService;
import config.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ConcertGetController.class)
@Import(TestSecurityConfig.class)
public class ConcertGetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConcertsService concertsService;

    @Test
    void shouldReturnConcertWhenExists() throws Exception {
        // Подготовка тестовых данных
        Integer concertId = 1;
        Concert concert = new Concert(1, "Moscow 1", "Concert 1", "ConcertVenue 1",
                LocalDate.of(2024, 12, 30), "ticketLink 1", "meetingLink 1");
        Optional<Concert> optionalConcert = Optional.of(concert);

        // Задаем поведение мока
        when(concertsService.getConcert(concertId)).thenReturn(optionalConcert);

        // Выполняем GET-запрос и проверяем результат
        mockMvc.perform(get("/api/v1/edit-concerts/get/concert/{id}", concertId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                    {
                                      "id": 1,
                                      "city": "Moscow 1",
                                      "description": "Concert 1",
                                      "concertVenue": "ConcertVenue 1",
                                      "date": "2024-12-30",
                                      "ticketsLink": "ticketLink 1",
                                      "meetingLink": "meetingLink 1"
                                    }
                                    """));
        // Проверяем, что метод был вызван
        verify(concertsService, times(1)).getConcert(concertId);
    }

    @Test
    void shouldReturnNotFoundWhenConcertDoesNotExist() throws Exception {
        // Подготовка тестовых данных
        Integer concertId = 999;

        // Задаем поведение мока
        when(concertsService.getConcert(concertId)).thenReturn(Optional.empty());

        // Выполняем GET-запрос и проверяем результат
        mockMvc.perform(get("/api/v1/edit-concerts/get/concert/{id}", concertId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldHandleInvalidIdFormat() throws Exception {
        // Выполняем GET-запрос с некорректным ID
        mockMvc.perform(get("/api/v1/edit-concerts/get/concert/{id}", "invalidId")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
