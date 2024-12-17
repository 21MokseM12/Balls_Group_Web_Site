package app.service.controllers.admin.concerts.concerts_management.impl;

import app.domain.entites.concerts.Concert;
import app.service.controllers.admin.concerts.concert.ConcertManagementService;
import app.service.controllers.admin.concerts.concerts_management.ConcertsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConcertsServiceImpl implements ConcertsService {

    @Autowired
    private ConcertManagementService concertService;

    @Override
    public List<Concert> getAllConcerts() {
        return concertService.getAllConcerts();
    }

    @Override
    public Optional<Concert> getConcert(Integer id) {
        return concertService.getConcert(id);
    }

    @Override
    public ResponseEntity<String> addConcert(Concert concert) {
        return concertService.addConcert(concert);
    }

    @Override
    public ResponseEntity<String> updateConcert(Concert concert) {
        return concertService.updateConcert(concert);
    }

    @Override
    public ResponseEntity<String> deleteConcert(Integer id) {
        return concertService.deleteConcert(id);
    }
}
