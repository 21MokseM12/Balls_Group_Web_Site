package app.service.controllers.admin.music.music_management.impl;

import app.domain.entites.music.Album;
import app.service.controllers.admin.music.albums.AlbumManagementService;
import app.service.controllers.admin.music.music_management.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MusicServiceImpl implements MusicService {

    @Autowired
    private AlbumManagementService albumService;

    @Override
    public List<Album> getAllAlbums() {
        return albumService.getAllAlbums();
    }

    @Override
    public Optional<Album> getAlbum(Long id) {
        return albumService.getAlbum(id);
    }

    @Override
    public ResponseEntity<String> addAlbum(Album album) {
        return albumService.addAlbum(album);
    }

    @Override
    public ResponseEntity<String> updateAlbum(Album album) {
        return albumService.updateAlbum(album);
    }

    @Override
    public ResponseEntity<String> deleteAlbum(Long id) {
        return albumService.deleteAlbum(id);
    }
}
