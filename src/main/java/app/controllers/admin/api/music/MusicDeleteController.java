package app.controllers.admin.api.music;

import app.service.controllers.admin.music.music_management.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/edit-music/delete/")
public class MusicDeleteController {

    @Autowired
    private MusicService musicService;

    @DeleteMapping("album/{id}")
    public ResponseEntity<String> deleteAlbum(@PathVariable Long id) {
        return musicService.deleteAlbum(id);
    }
}
