package app.controllers.admin.api.concerts;

import app.domain.entites.concerts.Concert;
import app.service.controllers.admin.concerts.concerts_management.ConcertsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/edit-concerts/add/")
public class ConcertPostController {

    @Autowired
    private ConcertsService concertsService;

    @PostMapping("concert/")
    public ResponseEntity<String> addConcert(@RequestBody Concert concert) {
        return concertsService.addConcert(concert);
    }
}
