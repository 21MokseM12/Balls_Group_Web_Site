package app.service.controllers.admin.concerts.concert.impl;

import app.domain.entites.concerts.Concert;
import app.repository.concerts.ConcertRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConcertServiceImplTest {

    @Mock
    private ConcertRepository concertRepository;

    @InjectMocks
    private ConcertServiceImpl concertService;

    @Test
    void findAll_ShouldReturnListOfConcerts() {
        // Arrange
        List<Concert> mockConcerts = List.of(new Concert(), new Concert());
        when(concertRepository.findAll()).thenReturn(mockConcerts);

        // Act
        List<Concert> result = concertService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(concertRepository, times(1)).findAll();
    }

    @Test
    void findById_ShouldReturnConcertOptional_WhenFound() {
        // Arrange
        Integer concertId = 1;
        Concert mockConcert = new Concert();
        when(concertRepository.findById(concertId)).thenReturn(Optional.of(mockConcert));

        // Act
        Optional<Concert> result = concertService.findById(concertId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(mockConcert, result.get());
        verify(concertRepository, times(1)).findById(concertId);
    }

    @Test
    void findById_ShouldReturnEmptyOptional_WhenNotFound() {
        // Arrange
        Integer concertId = 1;
        when(concertRepository.findById(concertId)).thenReturn(Optional.empty());

        // Act
        Optional<Concert> result = concertService.findById(concertId);

        // Assert
        assertTrue(result.isEmpty());
        verify(concertRepository, times(1)).findById(concertId);
    }

    @Test
    void exists_ShouldReturnTrue_WhenConcertExists() {
        // Arrange
        Concert concert = new Concert();
        concert.setCity("City");
        concert.setConcertVenue("Venue");
        concert.setDate(LocalDate.of(2024, 1, 1));
        when(concertRepository.existsByCityAndConcertVenueAndDate(
                concert.getCity(), concert.getConcertVenue(), concert.getDate()))
                .thenReturn(true);

        // Act
        boolean result = concertService.exists(concert);

        // Assert
        assertTrue(result);
        verify(concertRepository, times(1))
                .existsByCityAndConcertVenueAndDate(concert.getCity(), concert.getConcertVenue(), concert.getDate());
    }

    @Test
    void exists_ShouldReturnFalse_WhenConcertDoesNotExist() {
        // Arrange
        Concert concert = new Concert();
        concert.setCity("City");
        concert.setConcertVenue("Venue");
        concert.setDate(LocalDate.of(2024, 1, 1));
        when(concertRepository.existsByCityAndConcertVenueAndDate(
                concert.getCity(), concert.getConcertVenue(), concert.getDate()))
                .thenReturn(false);

        // Act
        boolean result = concertService.exists(concert);

        // Assert
        assertFalse(result);
        verify(concertRepository, times(1))
                .existsByCityAndConcertVenueAndDate(concert.getCity(), concert.getConcertVenue(), concert.getDate());
    }

    @Test
    void save_ShouldSaveConcert() {
        // Arrange
        Concert concert = new Concert();

        // Act
        concertService.save(concert);

        // Assert
        verify(concertRepository, times(1)).save(concert);
    }

    @Test
    void existsById_ShouldReturnTrue_WhenConcertExistsById() {
        // Arrange
        Integer concertId = 1;
        when(concertRepository.existsById(concertId)).thenReturn(true);

        // Act
        boolean result = concertService.existsById(concertId);

        // Assert
        assertTrue(result);
        verify(concertRepository, times(1)).existsById(concertId);
    }

    @Test
    void existsById_ShouldReturnFalse_WhenConcertDoesNotExistById() {
        // Arrange
        Integer concertId = 1;
        when(concertRepository.existsById(concertId)).thenReturn(false);

        // Act
        boolean result = concertService.existsById(concertId);

        // Assert
        assertFalse(result);
        verify(concertRepository, times(1)).existsById(concertId);
    }

    @Test
    void delete_ShouldDeleteConcertById() {
        // Arrange
        Integer concertId = 1;

        // Act
        concertService.delete(concertId);

        // Assert
        verify(concertRepository, times(1)).deleteById(concertId);
    }
}
