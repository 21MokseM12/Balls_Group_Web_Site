package app.service.controllers.admin.music.albums;

import app.domain.entites.music.Album;

import java.util.List;
import java.util.Optional;

public interface AlbumService {
    List<Album> findAll();

    Optional<Album> findById(Long id);

    boolean existsByTitle(String title);

    void save(Album album);

    boolean existsById(Long id);

    void delete(Long id);
}
