package app.controllers.admin.api.music;

import app.domain.entites.music.Album;
import app.service.controllers.admin.music.music_management.MusicService;
import config.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MusicPostController.class)
@Import(TestSecurityConfig.class)
public class MusicPostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MusicService musicService;

    @Test
    public void testAddAlbum_Success() throws Exception {
        // Мокируем сервис, чтобы метод addConcert возвращал успешный ответ
        when(musicService.addAlbum(any(Album.class)))
                .thenReturn(ResponseEntity.ok("Album added successfully"));

        // Выполняем POST-запрос и проверяем статус ответа
        mockMvc.perform(post("/api/v1/edit-music/add/album/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                 {
                                   "id": 1,
                                   "title": "Title 1",
                                   "description": "Album 1",
                                   "logoFileName": "file 1",
                                   "listenLink": "listenLink 1"
                                 }
                                 """))
                .andExpect(status().isOk())
                .andExpect(content().string("Album added successfully"));

        // Проверяем, что метод был вызван
        verify(musicService, times(1)).addAlbum(any(Album.class));
    }

    @Test
    public void testAddAlbum_Failure() throws Exception {
        // Мокируем сервис, чтобы метод addConcert возвращал ошибку
        when(musicService.addAlbum(any(Album.class)))
                .thenReturn(new ResponseEntity<>("Error adding album", HttpStatus.BAD_REQUEST));

        // Выполняем POST-запрос и проверяем статус ответа
        mockMvc.perform(post("/api/v1/edit-music/add/album/")
                        .contentType("application/json")
                        .content("{\"name\": \"Invalid Concert\", \"date\": \"2024-12-25\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error adding album"));
    }
}
