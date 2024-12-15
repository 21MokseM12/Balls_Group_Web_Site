package app.domain.entites.concerts;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

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

    private Date date;

    private String ticketsLink;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(schema = "concerts", name = "meeting_links", joinColumns = @JoinColumn(name = "concert_id"))
    @Enumerated(EnumType.STRING)
    private Set<String> meetingLink;
}
