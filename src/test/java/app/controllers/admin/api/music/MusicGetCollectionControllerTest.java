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

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MusicGetCollectionController.class)
@Import(TestSecurityConfig.class)
public class MusicGetCollectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MusicService musicService;

    @Test
    public void testGetCollectionAlbumSuccess() throws Exception {
        Album album1 = new Album(1L, "Title 1", "Album 1", "file 1", "listenLink 1");
        Album album2 = new Album(2L, "Title 2", "Album 2", "file 2", "listenLink 2");
        List<Album> albums = Arrays.asList(album1, album2);

        // Задаем поведение мока для интерфейса
        when(musicService.getAllAlbums()).thenReturn(albums);

        mockMvc.perform(get("/api/v1/edit-music/get-all/albums/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(
                      """
                                [
                                  {
                                  "id": 1,
                                  "title": "Title 1",
                                  "description": "Album 1",
                                  "logoFileName": "file 1",
                                  "listenLink": "listenLink 1"
                                  },
                                  {
                                  "id": 2,
                                  "title": "Title 2",
                                  "description": "Album 2",
                                  "logoFileName": "file 2",
                                  "listenLink": "listenLink 2"
                                  }
                                ]
                                """));

        // Проверяем, что метод был вызван
        verify(musicService, times(1)).getAllAlbums();
    }

    @Test
    public void testGetCollectionAlbumNotFound() throws Exception {
        // Задаем поведение мока
        when(musicService.getAllAlbums()).thenReturn(List.of());

        // Выполняем GET-запрос и проверяем результат
        mockMvc.perform(get("/api/v1/edit-music/get-all/albums/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
