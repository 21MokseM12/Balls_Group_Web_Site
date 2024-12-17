package app.controllers.admin.api.concerts;

import app.service.controllers.admin.concerts.concerts_management.ConcertsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/edit-concerts/delete/")
public class ConcertDeleteController {

    @Autowired
    private ConcertsService concertsService;

    @DeleteMapping("concert/{id}")
    public ResponseEntity<String> deleteConcert(@PathVariable Integer id) {
        return concertsService.deleteConcert(id);
    }
}
