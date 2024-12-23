package app.controllers.admin.api.concerts;

import app.service.controllers.admin.concerts.concerts_management.ConcertsService;
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

@WebMvcTest(controllers = ConcertDeleteController.class)
@Import(TestSecurityConfig.class)
public class ConcertDeleteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConcertsService concertsService; // Мокаем интерфейс ConcertsService

    @Test
    public void testDeleteConcertSuccess() throws Exception {
        Integer concertId = 1;

        // Задаем поведение мока для интерфейса
        when(concertsService.deleteConcert(concertId)).thenReturn(ResponseEntity.status(HttpStatus.OK).body("Concert deleted"));

        mockMvc.perform(delete("/api/v1/edit-concerts/delete/concert/{id}", concertId))
                .andExpect(status().isOk())
                .andExpect(content().string("Concert deleted"));

        // Проверяем, что метод был вызван
        verify(concertsService, times(1)).deleteConcert(concertId);
    }

    @Test
    public void testDeleteConcertNotFound() throws Exception {
        Integer concertId = 2;

        // Задаем поведение мока для интерфейса
        when(concertsService.deleteConcert(concertId)).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Concert not found"));

        mockMvc.perform(delete("/api/v1/edit-concerts/delete/concert/{id}", concertId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Concert not found"));

        verify(concertsService, times(1)).deleteConcert(concertId);
    }
}

