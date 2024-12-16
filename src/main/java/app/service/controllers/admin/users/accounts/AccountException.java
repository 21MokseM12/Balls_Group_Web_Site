package app.service.controllers.admin.users.accounts;

import jakarta.persistence.EntityNotFoundException;

public class AccountException extends EntityNotFoundException {

    public AccountException(String message) {
        super(message);
    }
}
