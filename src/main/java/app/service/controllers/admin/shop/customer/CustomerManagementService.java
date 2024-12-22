package app.service.controllers.admin.shop.customer;

import app.domain.entites.shop.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CustomerManagementService {

    @Autowired
    private CustomerService customerService;

    public ResponseEntity<String> addCustomer(Customer customer) {
        Long customerId;
        if (customerService.existsByName(customer.getName())) {
            customerId = customerService.findByName(customer.getName()).getId();
        } else {
            customerId = customerService.save(customer);
        }
        return ResponseEntity.ok(String.valueOf(customerId));
    }
}
