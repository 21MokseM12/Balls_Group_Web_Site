package app.repository.users;

import app.domain.entites.users.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findAccountByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsById(Long id);
}
