package app.service.controllers.admin.concerts.concert;

import app.domain.entites.concerts.Concert;

import java.util.List;
import java.util.Optional;

public interface ConcertService {
    List<Concert> findAll();

    Optional<Concert> findById(Integer id);

    boolean exists(Concert concert);

    void save(Concert concert);

    boolean existsById(Integer id);

    void delete(Integer id);
}
