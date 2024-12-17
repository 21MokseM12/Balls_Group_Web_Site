package app.service.controllers.admin.concerts.concert;

import app.domain.entites.concerts.Concert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConcertManagementService {

    @Autowired
    private ConcertService concertService;

    public List<Concert> getAllConcerts() {
        return concertService.findAll();
    }

    public Optional<Concert> getConcert(Integer id) {
        return concertService.findById(id);
    }

    public ResponseEntity<String> addConcert(Concert concert) {
        if (concertService.exists(concert)) {
            return ResponseEntity.ok("Концерт с такими данными уже существует");
        } else {
            concertService.save(concert);
            return ResponseEntity.ok("Концерт успешно добавлен!");
        }
    }

    public ResponseEntity<String> updateConcert(Concert concert) {
        if (concertService.existsById(concert.getId())) {
            concertService.save(concert);
            return ResponseEntity.ok("Концерт успешно обновлен!");
        } else {
            return ResponseEntity.ok("Концерт не был найден");
        }
    }

    public ResponseEntity<String> deleteConcert(Integer id) {
        concertService.delete(id);
        return ResponseEntity.ok("Концерт успешно удален");
    }
}
