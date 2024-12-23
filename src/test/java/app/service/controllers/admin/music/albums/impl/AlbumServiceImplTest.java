package app.service.controllers.admin.music.albums.impl;

import app.domain.entites.music.Album;
import app.repository.music.AlbumRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlbumServiceImplTest {

    @Mock
    private AlbumRepository albumRepository;

    @InjectMocks
    private AlbumServiceImpl albumService;

    @Test
    void findAll_ShouldReturnListOfAlbums() {
        // Arrange
        List<Album> mockAlbums = List.of(new Album(), new Album());
        when(albumRepository.findAll()).thenReturn(mockAlbums);

        // Act
        List<Album> result = albumService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(albumRepository, times(1)).findAll();
    }

    @Test
    void findById_ShouldReturnAlbumOptional_WhenFound() {
        // Arrange
        Long albumId = 1L;
        Album mockAlbum = new Album();
        when(albumRepository.findById(albumId)).thenReturn(Optional.of(mockAlbum));

        // Act
        Optional<Album> result = albumService.findById(albumId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(mockAlbum, result.get());
        verify(albumRepository, times(1)).findById(albumId);
    }

    @Test
    void findById_ShouldReturnEmptyOptional_WhenNotFound() {
        // Arrange
        Long albumId = 1L;
        when(albumRepository.findById(albumId)).thenReturn(Optional.empty());

        // Act
        Optional<Album> result = albumService.findById(albumId);

        // Assert
        assertTrue(result.isEmpty());
        verify(albumRepository, times(1)).findById(albumId);
    }

    @Test
    void exists_ShouldReturnTrue_WhenAlbumExists() {
        // Arrange
        Album album = new Album();
        album.setTitle("Title");
        when(albumRepository.existsByTitle(album.getTitle()))
                .thenReturn(true);

        // Act
        boolean result = albumService.existsByTitle(album.getTitle());

        // Assert
        assertTrue(result);
        verify(albumRepository, times(1))
                .existsByTitle(album.getTitle());
    }

    @Test
    void exists_ShouldReturnFalse_WhenAlbumDoesNotExist() {
        // Arrange
        Album album = new Album();
        album.setTitle("Title");
        when(albumRepository.existsByTitle(album.getTitle()))
                .thenReturn(false);

        // Act
        boolean result = albumService.existsByTitle(album.getTitle());

        // Assert
        assertFalse(result);
        verify(albumRepository, times(1))
                .existsByTitle(album.getTitle());
    }

    @Test
    void save_ShouldSaveAlbum() {
        // Arrange
        Album album = new Album();

        // Act
        albumService.save(album);

        // Assert
        verify(albumRepository, times(1)).save(album);
    }

    @Test
    void existsById_ShouldReturnTrue_WhenAlbumExistsById() {
        // Arrange
        Long albumId = 1L;
        when(albumRepository.existsById(albumId)).thenReturn(true);

        // Act
        boolean result = albumService.existsById(albumId);

        // Assert
        assertTrue(result);
        verify(albumRepository, times(1)).existsById(albumId);
    }

    @Test
    void existsById_ShouldReturnFalse_WhenAlbumDoesNotExistById() {
        // Arrange
        Long concertId = 1L;
        when(albumRepository.existsById(concertId)).thenReturn(false);

        // Act
        boolean result = albumService.existsById(concertId);

        // Assert
        assertFalse(result);
        verify(albumRepository, times(1)).existsById(concertId);
    }

    @Test
    void delete_ShouldDeleteAlbumById() {
        // Arrange
        Long albumId = 1L;

        // Act
        albumService.delete(albumId);

        // Assert
        verify(albumRepository, times(1)).deleteById(albumId);
    }
}
