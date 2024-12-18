package app.service.controllers.admin.shop.product;

import jakarta.persistence.EntityNotFoundException;

public class ProductException extends EntityNotFoundException {

    public ProductException(String message) {
        super(message);
    }
}
