package app.controllers.admin.api.music;

import app.domain.entites.music.Album;
import app.service.controllers.admin.music.music_management.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/edit-music/update/")
public class MusicUpdateController {

    @Autowired
    private MusicService musicService;

    @PutMapping("album/")
    public ResponseEntity<String> updateAlbum(@RequestBody Album album) {
        return musicService.updateAlbum(album);
    }
}
