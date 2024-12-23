package app.domain.entites.shop;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(schema = "shop", name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "ordered_product_id", nullable = false)
    private OrderedProduct orderedProduct;

    public Order(Customer customer, OrderedProduct orderedProduct) {
        this.customer = customer;
        this.orderedProduct = orderedProduct;
    }
}
