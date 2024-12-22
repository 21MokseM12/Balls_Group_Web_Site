package app.domain.entites.shop;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = "shop", name = "ordered_products")
@Getter
@Setter
public class OrderedProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Integer price;

    @ManyToOne
    @JoinColumn(name = "product_size_id")
    private ClothingSize size;
}
