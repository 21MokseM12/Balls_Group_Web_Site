package app.service.controllers.admin.shop.customer;

import app.domain.entites.shop.Customer;

import java.util.Optional;

public interface CustomerService {
    Long save(Customer customer);

    boolean existsByName(String name);

    Customer findByName(String name);

    Optional<Customer> findById(Long customerId);
}
