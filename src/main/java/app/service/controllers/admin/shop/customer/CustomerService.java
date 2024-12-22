package app.service.controllers.admin.shop.customer;

import app.domain.entites.shop.Customer;

public interface CustomerService {
    void save(Customer customer);

    boolean existsByName(String name);

    Customer findByName(String name);
}
