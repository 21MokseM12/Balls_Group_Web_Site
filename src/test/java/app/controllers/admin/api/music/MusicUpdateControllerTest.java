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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MusicUpdateController.class)
@Import(TestSecurityConfig.class)
public class MusicUpdateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MusicService musicService;

    @Test
    public void testUpdateAlbum_Success() throws Exception {
        // Мокируем сервис, чтобы метод updateConcert возвращал успешный ответ
        when(musicService.updateAlbum(any(Album.class)))
                .thenReturn(ResponseEntity.ok("Album updated successfully"));

        // Выполняем PUT-запрос и проверяем статус ответа и его содержимое
        mockMvc.perform(put("/api/v1/edit-music/update/album/")
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
                .andExpect(status().isOk())  // Проверка статус-кода 200 OK
                .andExpect(content().string("Album updated successfully"));  // Проверка содержимого тела ответа
    }

    @Test
    public void testUpdateAlbum_Failure() throws Exception {
        // Мокируем сервис, чтобы метод updateConcert возвращал ошибку
        when(musicService.updateAlbum(any(Album.class)))
                .thenReturn(new ResponseEntity<>("Error updating album", HttpStatus.BAD_REQUEST));

        // Выполняем PUT-запрос и проверяем статус ответа и его содержимое
        mockMvc.perform(put("/api/v1/edit-music/update/album/")
                        .contentType("application/json")
                        .content("""
                                  {
                                      "id": 1,
                                      "title": "Title 1",
                                      "description": "Album 1",
                                      "logoFileName": "file 1",
                                      "listenLink": "listenLink 1"
                                  }
                             """))
                .andExpect(status().isBadRequest())  // Проверка статус-кода 400 Bad Request
                .andExpect(content().string("Error updating album"));  // Проверка содержимого тела ответа
    }
}
