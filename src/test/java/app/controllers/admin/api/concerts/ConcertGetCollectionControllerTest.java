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
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ConcertGetCollectionController.class)
@Import(TestSecurityConfig.class)
public class ConcertGetCollectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConcertsService concertsService; // Мокаем интерфейс ConcertsService

    @Test
    public void testGetCollectionConcertSuccess() throws Exception {
        Concert concert1 = new Concert(1, "Moscow 1", "Concert 1", "ConcertVenue 1",
                LocalDate.of(2024, 12, 30), "ticketLink 1", "meetingLink 1");
        Concert concert2 = new Concert(2, "Moscow 2", "Concert 2", "ConcertVenue 2",
                LocalDate.of(2024, 12, 29), "ticketLink 2", "meetingLink 2");
        List<Concert> concerts = Arrays.asList(concert1, concert2);

        // Задаем поведение мока для интерфейса
        when(concertsService.getAllConcerts()).thenReturn(concerts);

        mockMvc.perform(get("/api/v1/edit-concerts/get-all/concerts/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(
                      """
                                [
                                  {
                                  "id": 1,
                                  "city": "Moscow 1",
                                  "description": "Concert 1",
                                  "concertVenue": "ConcertVenue 1",
                                  "date": "2024-12-30",
                                  "ticketsLink": "ticketLink 1",
                                  "meetingLink": "meetingLink 1"
                                  },
                                  {
                                  "id": 2,
                                  "city": "Moscow 2",
                                  "description": "Concert 2",
                                  "concertVenue": "ConcertVenue 2",
                                  "date": "2024-12-29",
                                  "ticketsLink": "ticketLink 2",
                                  "meetingLink": "meetingLink 2"
                                  }
                                ]
                                """));

        // Проверяем, что метод был вызван
        verify(concertsService, times(1)).getAllConcerts();
    }

    @Test
    public void testGetCollectionConcertNotFound() throws Exception {
        // Задаем поведение мока
        when(concertsService.getAllConcerts()).thenReturn(List.of());

        // Выполняем GET-запрос и проверяем результат
        mockMvc.perform(get("/api/v1/edit-concerts/get-all/concerts/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
