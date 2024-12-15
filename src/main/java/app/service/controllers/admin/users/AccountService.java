package app.service.controllers.admin.users;

import app.domain.entites.users.Account;

import java.util.List;

public interface AccountService {
    boolean existsByUsername(String username);

    void saveAccount(Account account);

    boolean existsById(Long id);

    List<Account> findAllAccounts();

    void deleteById(Long id);
}
