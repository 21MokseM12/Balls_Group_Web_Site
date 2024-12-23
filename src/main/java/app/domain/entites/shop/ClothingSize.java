package app.domain.entites.shop;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(schema = "shop", name = "sizes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClothingSize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String size;

    public ClothingSize(String size) {
        this.size = size;
    }
}
