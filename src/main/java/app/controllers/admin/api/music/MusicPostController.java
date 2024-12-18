package app.controllers.admin.api.music;

import app.domain.entites.music.Album;
import app.service.controllers.admin.music.music_management.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/edit-music/add/")
public class MusicPostController {

    @Autowired
    private MusicService musicService;

    @PostMapping("album/")
    public ResponseEntity<String> addAlbum(@RequestBody Album album) {
        return musicService.addAlbum(album);
    }
}
