package app.service.controllers.admin.shop.category;

import jakarta.persistence.EntityNotFoundException;

public class CategoryException extends EntityNotFoundException {

    public CategoryException(String message) {
        super(message);
    }
}
