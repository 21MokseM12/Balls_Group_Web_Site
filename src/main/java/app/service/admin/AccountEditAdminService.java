package app.service.admin;

import app.domain.users.Account;
import app.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountEditAdminService {

    @Autowired
    private AccountRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public void saveAccount(Account account) {
        repository.save(account);
    }

    public List<Account> findAllAccounts() {
        return (List<Account>) repository.findAll();
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public String encodeAccountPassword(String password) {
        return encoder.encode(password);
    }
}
