package app.service.controllers.admin.concerts.concert;

import app.domain.entites.concerts.Concert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConcertManagementServiceTest {

    @Mock
    private ConcertService concertService;

    @InjectMocks
    private ConcertManagementService concertManagementService;

    @Test
    void getAllConcerts_ShouldReturnListOfConcerts() {
        // Arrange
        List<Concert> mockConcerts = List.of(new Concert(), new Concert());
        when(concertService.findAll()).thenReturn(mockConcerts);

        // Act
        List<Concert> result = concertManagementService.getAllConcerts();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(concertService, times(1)).findAll();
    }

    @Test
    void getConcert_ShouldReturnConcertOptional_WhenFound() {
        // Arrange
        Integer concertId = 1;
        Optional<Concert> mockConcert = Optional.of(new Concert());
        when(concertService.findById(concertId)).thenReturn(mockConcert);

        // Act
        Optional<Concert> result = concertManagementService.getConcert(concertId);

        // Assert
        assertTrue(result.isPresent());
        verify(concertService, times(1)).findById(concertId);
    }

    @Test
    void getConcert_ShouldReturnEmptyOptional_WhenNotFound() {
        // Arrange
        Integer concertId = 1;
        when(concertService.findById(concertId)).thenReturn(Optional.empty());

        // Act
        Optional<Concert> result = concertManagementService.getConcert(concertId);

        // Assert
        assertTrue(result.isEmpty());
        verify(concertService, times(1)).findById(concertId);
    }

    @Test
    void addConcert_ShouldReturnBadRequest_WhenConcertExists() {
        // Arrange
        Concert concert = new Concert();
        when(concertService.exists(concert)).thenReturn(true);

        // Act
        ResponseEntity<String> response = concertManagementService.addConcert(concert);

        // Assert
        assertEquals(ResponseEntity.badRequest().body("Концерт с такими данными уже существует"), response);
        verify(concertService, times(1)).exists(concert);
        verify(concertService, never()).save(any());
    }

    @Test
    void addConcert_ShouldSaveConcert_WhenConcertDoesNotExist() {
        // Arrange
        Concert concert = new Concert();
        when(concertService.exists(concert)).thenReturn(false);

        // Act
        ResponseEntity<String> response = concertManagementService.addConcert(concert);

        // Assert
        assertEquals(ResponseEntity.ok("Концерт успешно добавлен!"), response);
        verify(concertService, times(1)).exists(concert);
        verify(concertService, times(1)).save(concert);
    }

    @Test
    void updateConcert_ShouldUpdateConcert_WhenConcertExists() {
        // Arrange
        Concert concert = new Concert();
        concert.setId(1);
        when(concertService.existsById(concert.getId())).thenReturn(true);

        // Act
        ResponseEntity<String> response = concertManagementService.updateConcert(concert);

        // Assert
        assertEquals(ResponseEntity.ok("Концерт успешно обновлен!"), response);
        verify(concertService, times(1)).existsById(concert.getId());
        verify(concertService, times(1)).save(concert);
    }

    @Test
    void updateConcert_ShouldReturnNotFound_WhenConcertDoesNotExist() {
        // Arrange
        Concert concert = new Concert();
        concert.setId(1);
        when(concertService.existsById(concert.getId())).thenReturn(false);

        // Act
        ResponseEntity<String> response = concertManagementService.updateConcert(concert);

        // Assert
        assertEquals(ResponseEntity.ok("Концерт не был найден"), response);
        verify(concertService, times(1)).existsById(concert.getId());
        verify(concertService, never()).save(any());
    }

    @Test
    void deleteConcert_ShouldCallDeleteMethod() {
        // Arrange
        Integer concertId = 1;

        // Act
        ResponseEntity<String> response = concertManagementService.deleteConcert(concertId);

        // Assert
        assertEquals(ResponseEntity.ok("Концерт успешно удален"), response);
        verify(concertService, times(1)).delete(concertId);
    }
}
