package app.controllers.admin.api.concerts;

import app.domain.entites.concerts.Concert;
import app.service.controllers.admin.concerts.concerts_management.ConcertsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/edit-concerts/get-all/")
public class ConcertGetCollectionController {

    @Autowired
    private ConcertsService concertsService;

    @GetMapping("concerts/")
    public List<Concert> getAllConcerts() {
        return concertsService.getAllConcerts();
    }
}
