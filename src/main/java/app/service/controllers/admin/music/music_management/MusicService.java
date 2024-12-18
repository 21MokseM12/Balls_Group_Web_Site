package app.service.controllers.admin.music.music_management;

import app.domain.entites.music.Album;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface MusicService {

    List<Album> getAllAlbums();

    Optional<Album> getAlbum(Long id);

    ResponseEntity<String> addAlbum(Album album);

    ResponseEntity<String> updateAlbum(Album album);

    ResponseEntity<String> deleteAlbum(Long id);
}
