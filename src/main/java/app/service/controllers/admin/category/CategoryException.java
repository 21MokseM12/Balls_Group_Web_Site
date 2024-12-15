package app.service.controllers.admin.category;

import com.amazonaws.services.appstream.model.EntitlementNotFoundException;

public class CategoryException extends EntitlementNotFoundException {

    public CategoryException(String message) {
        super(message);
    }
}
