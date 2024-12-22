package app.service.controllers.admin.shop.customer.impl;

import app.domain.entites.shop.Customer;
import app.repository.shop.CustomerRepository;
import app.service.controllers.admin.shop.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public boolean existsByName(String name) {
        return customerRepository.existsByName(name);
    }

    @Override
    public Customer findByName(String name) {
        return customerRepository.findByName(name);
    }
}
