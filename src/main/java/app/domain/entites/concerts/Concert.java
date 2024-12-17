package app.domain.entites.concerts;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(schema = "concerts", name = "concert")
public class Concert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String city;

    private String description;

    private String concertVenue;

    private LocalDate date;

    private String ticketsLink;

    private String meetingLink;
}
