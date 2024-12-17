package app.repository;

import app.domain.entites.concerts.Concert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;

@Repository
public interface ConcertRepository extends JpaRepository<Concert, Integer> {
    boolean existsByCityAndConcertVenueAndDate(String city, String venue, LocalDate date);
}
