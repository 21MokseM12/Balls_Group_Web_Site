package app.service.controllers.admin.concerts.concert.impl;

import app.domain.entites.concerts.Concert;
import app.repository.concerts.ConcertRepository;
import app.service.controllers.admin.concerts.concert.ConcertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ConcertServiceImpl implements ConcertService {

    @Autowired
    private ConcertRepository concertRepository;

    @Override
    public List<Concert> findAll() {
        return concertRepository.findAll();
    }

    @Override
    public Optional<Concert> findById(Integer id) {
        return concertRepository.findById(id);
    }

    @Override
    public boolean exists(Concert concert) {
        return concertRepository.existsByCityAndConcertVenueAndDate(
                concert.getCity(),
                concert.getConcertVenue(),
                concert.getDate()
        );
    }

    @Override
    public void save(Concert concert) {
        concertRepository.save(concert);
    }

    @Override
    public boolean existsById(Integer id) {
        return concertRepository.existsById(id);
    }

    @Override
    public void delete(Integer id) {
        concertRepository.deleteById(id);
    }
}
