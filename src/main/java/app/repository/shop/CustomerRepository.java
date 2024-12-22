package app.repository.shop;

import app.domain.entites.shop.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByName(String name);

    Customer findByName(String name);
}
