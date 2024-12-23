package app.service.controllers.admin.music.music_management.impl;

import app.domain.entites.music.Album;
import app.service.controllers.admin.music.albums.AlbumManagementService;
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
public class MusicServiceImplTest {

    @Mock
    private AlbumManagementService albumManagementService;

    @InjectMocks
    private MusicServiceImpl musicService;

    @Test
    void getAllAlbums_ShouldReturnListOfAlbums() {
        // Arrange
        List<Album> mockAlbums = List.of(new Album(), new Album());
        when(albumManagementService.getAllAlbums()).thenReturn(mockAlbums);

        // Act
        List<Album> result = musicService.getAllAlbums();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(albumManagementService, times(1)).getAllAlbums();
    }

    @Test
    void getAlbum_ShouldReturnAlbumOptional_WhenFound() {
        // Arrange
        Long albumId = 1L;
        Optional<Album> mockAlbum = Optional.of(new Album());
        when(albumManagementService.getAlbum(albumId)).thenReturn(mockAlbum);

        // Act
        Optional<Album> result = musicService.getAlbum(albumId);

        // Assert
        assertTrue(result.isPresent());
        verify(albumManagementService, times(1)).getAlbum(albumId);
    }

    @Test
    void addAlbum_ShouldDelegateCallToAlbumService() {
        // Arrange
        Album album = new Album();
        ResponseEntity<String> mockResponse = ResponseEntity.ok("Album added");
        when(albumManagementService.addAlbum(album)).thenReturn(mockResponse);

        // Act
        ResponseEntity<String> result = musicService.addAlbum(album);

        // Assert
        assertNotNull(result);
        assertEquals("Album added", result.getBody());
        verify(albumManagementService, times(1)).addAlbum(album);
    }

    @Test
    void updateAlbum_ShouldDelegateCallToAlbumService() {
        // Arrange
        Album album = new Album();
        ResponseEntity<String> mockResponse = ResponseEntity.ok("Album updated");
        when(albumManagementService.updateAlbum(album)).thenReturn(mockResponse);

        // Act
        ResponseEntity<String> result = musicService.updateAlbum(album);

        // Assert
        assertNotNull(result);
        assertEquals("Album updated", result.getBody());
        verify(albumManagementService, times(1)).updateAlbum(album);
    }

    @Test
    void deleteAlbum_ShouldDelegateCallToAlbumService() {
        // Arrange
        Long albumId = 1L;
        ResponseEntity<String> mockResponse = ResponseEntity.ok("Album deleted");
        when(albumManagementService.deleteAlbum(albumId)).thenReturn(mockResponse);

        // Act
        ResponseEntity<String> result = musicService.deleteAlbum(albumId);

        // Assert
        assertNotNull(result);
        assertEquals("Album deleted", result.getBody());
        verify(albumManagementService, times(1)).deleteAlbum(albumId);
    }
}
