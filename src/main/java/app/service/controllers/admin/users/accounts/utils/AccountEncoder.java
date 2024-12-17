package app.service.controllers.admin.users.accounts.utils;

import app.domain.entites.users.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AccountEncoder {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void encodePassword(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
    }
}
