package app.domain.shop;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "products", schema = "shop")
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productTitle;

    private String description;

    private Integer price;

    private boolean isStock;

    @ElementCollection(targetClass = ClothingSize.class, fetch = FetchType.EAGER)
    @CollectionTable(schema = "shop", name = "clothes_size", joinColumns = @JoinColumn(name = "product_id"))
    @Enumerated(EnumType.STRING)
    private Set<ClothingSize> clothingSize;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(schema = "shop", name = "product_photo_links", joinColumns = @JoinColumn(name = "product_id"))
    private Set<String> productPhotoLinks;
}
