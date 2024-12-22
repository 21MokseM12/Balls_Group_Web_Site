package app.service.controllers.admin.shop.order.product;

import jakarta.persistence.EntityNotFoundException;

public class OrderedProductException extends EntityNotFoundException {

    public OrderedProductException(String message) {
        super(message);
    }
}
