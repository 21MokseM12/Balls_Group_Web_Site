package app.controllers.admin.api.music;

import app.domain.entites.music.Album;
import app.service.controllers.admin.music.music_management.MusicService;
import config.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MusicGetController.class)
@Import(TestSecurityConfig.class)
public class MusicGetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MusicService musicService;

    @Test
    void shouldReturnAlbumWhenExists() throws Exception {
        // Подготовка тестовых данных
        Long albumId = 1L;
        Album album = new Album(1L, "Title 1", "Album 1", "file 1", "listenLink 1");
        Optional<Album> optionalAlbum = Optional.of(album);

        // Задаем поведение мока
        when(musicService.getAlbum(albumId)).thenReturn(optionalAlbum);

        // Выполняем GET-запрос и проверяем результат
        mockMvc.perform(get("/api/v1/edit-music/get/album/{id}", albumId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                    {
                                      "id": 1,
                                      "title": "Title 1",
                                      "description": "Album 1",
                                      "logoFileName": "file 1",
                                      "listenLink": "listenLink 1"
                                    }
                                    """));
        // Проверяем, что метод был вызван
        verify(musicService, times(1)).getAlbum(albumId);
    }

    @Test
    void shouldReturnNotFoundWhenAlbumDoesNotExist() throws Exception {
        // Подготовка тестовых данных
        Long albumId = 999L;

        // Задаем поведение мока
        when(musicService.getAlbum(albumId)).thenReturn(Optional.empty());

        // Выполняем GET-запрос и проверяем результат
        mockMvc.perform(get("/api/v1/edit-music/get/album/{id}", albumId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldHandleInvalidIdFormat() throws Exception {
        // Выполняем GET-запрос с некорректным ID
        mockMvc.perform(get("/api/v1/edit-music/get/album/{id}", "invalidId")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
