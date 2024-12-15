package app.service.controllers.admin.product;

import com.amazonaws.services.appstream.model.EntitlementNotFoundException;

public class ProductException extends EntitlementNotFoundException {

    public ProductException(String message) {
        super(message);
    }
}
