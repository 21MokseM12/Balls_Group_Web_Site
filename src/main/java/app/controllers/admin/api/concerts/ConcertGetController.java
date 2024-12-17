package app.controllers.admin.api.concerts;

import app.domain.entites.concerts.Concert;
import app.service.controllers.admin.concerts.concerts_management.ConcertsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/edit-concerts/get/")
public class ConcertGetController {

    @Autowired
    private ConcertsService concertsService;

    @GetMapping("concert/{id}")
    public Optional<Concert> getConcert(@PathVariable Integer id) {
        return concertsService.getConcert(id);
    }
}
