package app.service.controllers.admin.shop.clothing;

import com.amazonaws.services.appstream.model.EntitlementNotFoundException;

public class ClothingSizeException extends EntitlementNotFoundException {

    public ClothingSizeException(String message) {
        super(message);
    }
}
