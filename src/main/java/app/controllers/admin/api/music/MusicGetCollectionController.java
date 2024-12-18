package app.controllers.admin.api.music;

import app.domain.entites.music.Album;
import app.service.controllers.admin.music.music_management.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/edit-music/get-all/")
public class MusicGetCollectionController {

    @Autowired
    private MusicService musicService;

    @GetMapping("albums/")
    public List<Album> getAllAlbums() {
        return musicService.getAllAlbums();
    }
}
