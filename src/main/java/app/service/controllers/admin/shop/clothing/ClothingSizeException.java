package app.service.controllers.admin.shop.clothing;

import jakarta.persistence.EntityNotFoundException;

public class ClothingSizeException extends EntityNotFoundException {

    public ClothingSizeException(String message) {
        super(message);
    }
}
