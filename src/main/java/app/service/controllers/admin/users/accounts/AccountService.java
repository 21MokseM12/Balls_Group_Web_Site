package app.service.controllers.admin.users.accounts;

import app.domain.entites.users.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    List<Account> findAll();

    Optional<Account> findById(Long id);

    boolean existsByUsername(String username);

    void save(Account account);

    boolean existsById(Long id);

    void deleteById(Long id);
}
