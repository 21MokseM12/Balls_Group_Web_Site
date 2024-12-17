package app.service.controllers.admin.concerts.concerts_management;

import app.domain.entites.concerts.Concert;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ConcertsService {
    List<Concert> getAllConcerts();

    Optional<Concert> getConcert(Integer id);

    ResponseEntity<String> addConcert(Concert concert);

    ResponseEntity<String> updateConcert(Concert concert);

    ResponseEntity<String> deleteConcert(Integer id);
}
