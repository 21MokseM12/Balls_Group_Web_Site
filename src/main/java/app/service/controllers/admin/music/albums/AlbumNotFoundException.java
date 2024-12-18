package app.service.controllers.admin.music.albums;

import jakarta.persistence.EntityNotFoundException;

public class AlbumNotFoundException extends EntityNotFoundException {

    public AlbumNotFoundException(String message) {
        super(message);
    }
}
