package app.repository.music;

import app.domain.entites.music.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    boolean existsByTitle(String title);
}
