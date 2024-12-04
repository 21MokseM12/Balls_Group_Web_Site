package app.repository;

import app.domain.users.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {
    Optional<Account> findAccountByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsById(Long id);
}
