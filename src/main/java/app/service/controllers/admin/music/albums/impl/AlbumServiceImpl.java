package app.service.controllers.admin.music.albums.impl;

import app.domain.entites.music.Album;
import app.repository.AlbumRepository;
import app.service.controllers.admin.music.albums.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    @Override
    public List<Album> findAll() {
        return albumRepository.findAll();
    }

    @Override
    public Optional<Album> findById(Long id) {
        return albumRepository.findById(id);
    }

    @Override
    public boolean existsByTitle(String title) {
        return albumRepository.existsByTitle(title);
    }

    @Override
    public void save(Album album) {
        albumRepository.save(album);
    }

    @Override
    public boolean existsById(Long id) {
        return albumRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        albumRepository.deleteById(id);
    }
}
