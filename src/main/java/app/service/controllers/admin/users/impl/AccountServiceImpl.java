package app.service.controllers.admin.users.impl;

import app.domain.entites.users.Account;
import app.repository.AccountRepository;
import app.service.controllers.admin.users.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean existsByUsername(String username) {
        return accountRepository.existsByUsername(username);
    }

    public boolean existsById(Long id) {
        return accountRepository.existsById(id);
    }

    public void saveAccount(Account account) {
        encodeAccountPassword(account);
        accountRepository.save(account);
    }

    public List<Account> findAllAccounts() {
        return accountRepository.findAll();
    }

    public void deleteById(Long id) {
        accountRepository.deleteById(id);
    }

    private void encodeAccountPassword(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
    }
}
