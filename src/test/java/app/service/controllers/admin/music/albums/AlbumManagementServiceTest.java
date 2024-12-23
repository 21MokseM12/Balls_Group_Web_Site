package app.service.controllers.admin.music.albums;

import app.domain.entites.music.Album;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlbumManagementServiceTest {

    @Mock
    private AlbumService albumService;

    @InjectMocks
    private AlbumManagementService albumManagementService;

    @Test
    void getAllAlbums_ShouldReturnListOfAlbums() {
        // Arrange
        List<Album> mockConcerts = List.of(new Album(), new Album());
        when(albumService.findAll()).thenReturn(mockConcerts);

        // Act
        List<Album> result = albumManagementService.getAllAlbums();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(albumService, times(1)).findAll();
    }

    @Test
    void getAlbum_ShouldReturnAlbumOptional_WhenFound() {
        // Arrange
        Long albumId = 1L;
        Optional<Album> optionalAlbum = Optional.of(new Album());
        when(albumService.findById(albumId)).thenReturn(optionalAlbum);

        // Act
        Optional<Album> result = albumManagementService.getAlbum(albumId);

        // Assert
        assertTrue(result.isPresent());
        verify(albumService, times(1)).findById(albumId);
    }

    @Test
    void getAlbum_ShouldReturnEmptyOptional_WhenNotFound() {
        // Arrange
        Long albumId = 1L;
        when(albumService.findById(albumId)).thenReturn(Optional.empty());

        // Act
        Optional<Album> result = albumManagementService.getAlbum(albumId);

        // Assert
        assertTrue(result.isEmpty());
        verify(albumService, times(1)).findById(albumId);
    }

    @Test
    void addAlbum_ShouldReturnBadRequest_WhenAlbumExists() {
        // Arrange
        Album album = new Album();
        when(albumService.existsByTitle(album.getTitle())).thenReturn(true);

        // Act
        ResponseEntity<String> response = albumManagementService.addAlbum(album);

        // Assert
        assertEquals(ResponseEntity.badRequest().body("Альбом с таким названием уже существует"), response);
        verify(albumService, times(1)).existsByTitle(album.getTitle());
        verify(albumService, never()).save(any());
    }

    @Test
    void addAlbum_ShouldSaveAlbum_WhenAlbumDoesNotExist() {
        // Arrange
        Album album = new Album();
        when(albumService.existsByTitle(album.getTitle())).thenReturn(false);

        // Act
        ResponseEntity<String> response = albumManagementService.addAlbum(album);

        // Assert
        assertEquals(ResponseEntity.ok("Альбом успешно добавлен!"), response);
        verify(albumService, times(1)).existsByTitle(album.getTitle());
        verify(albumService, times(1)).save(album);
    }

    @Test
    void updateAlbum_ShouldUpdateAlbum_WhenAlbumExists() {
        // Arrange
        Album album = new Album();
        album.setId(1L);
        when(albumService.existsById(album.getId())).thenReturn(true);

        // Act
        ResponseEntity<String> response = albumManagementService.updateAlbum(album);

        // Assert
        assertEquals(ResponseEntity.ok("Альбом успешно обновлен!"), response);
        verify(albumService, times(1)).existsById(album.getId());
        verify(albumService, times(1)).save(album);
    }

    @Test
    void updateAlbum_ShouldReturnNotFound_WhenAlbumDoesNotExist() {
        // Arrange
        Album album = new Album();
        album.setId(1L);
        when(albumService.existsById(album.getId())).thenReturn(false);

        // Act
        ResponseEntity<String> response = albumManagementService.updateAlbum(album);

        // Assert
        assertEquals(ResponseEntity.badRequest().body("Альбом не был найден"), response);
        verify(albumService, times(1)).existsById(album.getId());
        verify(albumService, never()).save(any());
    }

    @Test
    void deleteAlbum_ShouldCallDeleteMethod() {
        // Arrange
        Long albumId = 1L;

        // Act
        ResponseEntity<String> response = albumManagementService.deleteAlbum(albumId);

        // Assert
        assertEquals(ResponseEntity.ok("Альбом успешно удален"), response);
        verify(albumService, times(1)).delete(albumId);
    }
}
