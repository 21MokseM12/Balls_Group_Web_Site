package app.controllers.admin.api.concerts;

import app.domain.entites.concerts.Concert;
import app.service.controllers.admin.concerts.concerts_management.ConcertsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/edit-concerts/update/")
public class ConcertUpdateController {

    @Autowired
    private ConcertsService concertsService;

    @PutMapping("concert/")
    public ResponseEntity<String> updateConcert(@RequestBody Concert concert) {
        return concertsService.updateConcert(concert);
    }
}
