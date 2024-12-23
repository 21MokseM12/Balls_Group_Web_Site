package app.service.controllers.admin.concerts.concerts_management.impl;

import app.domain.entites.concerts.Concert;
import app.service.controllers.admin.concerts.concert.ConcertManagementService;
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
public class ConcertsServiceImplTest {

    @Mock
    private ConcertManagementService concertService;

    @InjectMocks
    private ConcertsServiceImpl concertsService;

    @Test
    void getAllConcerts_ShouldReturnListOfConcerts() {
        // Arrange
        List<Concert> mockConcerts = List.of(new Concert(), new Concert());
        when(concertService.getAllConcerts()).thenReturn(mockConcerts);

        // Act
        List<Concert> result = concertsService.getAllConcerts();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(concertService, times(1)).getAllConcerts();
    }

    @Test
    void getConcert_ShouldReturnConcertOptional_WhenFound() {
        // Arrange
        Integer concertId = 1;
        Optional<Concert> mockConcert = Optional.of(new Concert());
        when(concertService.getConcert(concertId)).thenReturn(mockConcert);

        // Act
        Optional<Concert> result = concertsService.getConcert(concertId);

        // Assert
        assertTrue(result.isPresent());
        verify(concertService, times(1)).getConcert(concertId);
    }

    @Test
    void addConcert_ShouldDelegateCallToConcertService() {
        // Arrange
        Concert concert = new Concert();
        ResponseEntity<String> mockResponse = ResponseEntity.ok("Concert added");
        when(concertService.addConcert(concert)).thenReturn(mockResponse);

        // Act
        ResponseEntity<String> result = concertsService.addConcert(concert);

        // Assert
        assertNotNull(result);
        assertEquals("Concert added", result.getBody());
        verify(concertService, times(1)).addConcert(concert);
    }

    @Test
    void updateConcert_ShouldDelegateCallToConcertService() {
        // Arrange
        Concert concert = new Concert();
        ResponseEntity<String> mockResponse = ResponseEntity.ok("Concert updated");
        when(concertService.updateConcert(concert)).thenReturn(mockResponse);

        // Act
        ResponseEntity<String> result = concertsService.updateConcert(concert);

        // Assert
        assertNotNull(result);
        assertEquals("Concert updated", result.getBody());
        verify(concertService, times(1)).updateConcert(concert);
    }

    @Test
    void deleteConcert_ShouldDelegateCallToConcertService() {
        // Arrange
        Integer concertId = 1;
        ResponseEntity<String> mockResponse = ResponseEntity.ok("Concert deleted");
        when(concertService.deleteConcert(concertId)).thenReturn(mockResponse);

        // Act
        ResponseEntity<String> result = concertsService.deleteConcert(concertId);

        // Assert
        assertNotNull(result);
        assertEquals("Concert deleted", result.getBody());
        verify(concertService, times(1)).deleteConcert(concertId);
    }
}
