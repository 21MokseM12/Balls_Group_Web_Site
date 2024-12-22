package app.service.controllers.admin.shop.customer;

import jakarta.persistence.EntityNotFoundException;

public class CustomerNotFoundException extends EntityNotFoundException {

    public CustomerNotFoundException(String message) {
        super(message);
    }
}
