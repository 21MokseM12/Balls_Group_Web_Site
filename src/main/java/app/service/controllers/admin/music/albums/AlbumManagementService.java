package app.service.controllers.admin.music.albums;

import app.domain.entites.music.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlbumManagementService {

    @Autowired
    private AlbumService albumService;

    public List<Album> getAllAlbums() {
        return albumService.findAll();
    }

    public Optional<Album> getAlbum(Long id) {
        return albumService.findById(id);
    }

    public ResponseEntity<String> addAlbum(Album album) {
        if (albumService.existsByTitle(album.getTitle())) {
            return ResponseEntity.badRequest().body("Альбом с таким названием уже существует");
        } else {
            albumService.save(album);
            return ResponseEntity.ok("Альбом успешно добавлен!");
        }
    }

    public ResponseEntity<String> updateAlbum(Album albumDTO) {
        if (albumService.existsById(albumDTO.getId())) {
            albumService.save(albumDTO);
            return ResponseEntity.ok("Альбом успешно обновлен!");
        } else {
            return ResponseEntity.badRequest().body("Альбом не был найден");
        }
    }

    public ResponseEntity<String> deleteAlbum(Long id) {
        albumService.delete(id);
        return ResponseEntity.ok("Альбом успешно удален");
    }
}
