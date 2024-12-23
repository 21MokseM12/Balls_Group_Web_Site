package app.controllers.admin.api.music;

import app.service.controllers.admin.music.music_management.MusicService;
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

@WebMvcTest(controllers = MusicDeleteController.class)
@Import(TestSecurityConfig.class)
public class MusicDeleteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MusicService musicService; // Мокаем интерфейс

    @Test
    public void testDeleteAlbumSuccess() throws Exception {
        Long albumId = 1L;

        // Задаем поведение мока для интерфейса
        when(musicService.deleteAlbum(albumId)).thenReturn(ResponseEntity.status(HttpStatus.OK).body("Album deleted"));

        mockMvc.perform(delete("/api/v1/edit-music/delete/album/{id}", albumId))
                .andExpect(status().isOk())
                .andExpect(content().string("Album deleted"));

        // Проверяем, что метод был вызван
        verify(musicService, times(1)).deleteAlbum(albumId);
    }

    @Test
    public void testDeleteAlbumNotFound() throws Exception {
        Long albumId = 2L;

        // Задаем поведение мока для интерфейса
        when(musicService.deleteAlbum(albumId)).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Album not found"));

        mockMvc.perform(delete("/api/v1/edit-music/delete/album/{id}", albumId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Album not found"));

        verify(musicService, times(1)).deleteAlbum(albumId);
    }
}

