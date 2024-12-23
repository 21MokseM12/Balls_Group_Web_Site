package app.domain.entites.shop;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "products", schema = "shop")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private Integer price;

    private int quantityInStock;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "category_id", nullable = false)
    private Category category;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    @JoinTable(
            schema = "shop",
            name = "products_sizes",
            joinColumns = { @JoinColumn(name = "product_id") },
            inverseJoinColumns = { @JoinColumn(name = "size_id") }
    )
    private Set<ClothingSize> clothingSize;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(schema = "shop", name = "product_photo_links", joinColumns = @JoinColumn(name = "product_id"))
    private Set<String> productPhotoLinks;
}
