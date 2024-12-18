package app.controllers.admin.api.music;

import app.domain.entites.music.Album;
import app.service.controllers.admin.music.music_management.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/edit-music/get/")
public class MusicGetController {

    @Autowired
    private MusicService musicService;

    @GetMapping("album/{id}")
    public Optional<Album> getAlbum(@PathVariable Long id) {
        return musicService.getAlbum(id);
    }
}
