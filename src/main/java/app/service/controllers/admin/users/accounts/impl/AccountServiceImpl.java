package app.service.controllers.admin.users.accounts.impl;

import app.domain.entites.users.Account;
import app.repository.AccountRepository;
import app.service.controllers.admin.users.accounts.AccountService;
import app.service.controllers.admin.users.accounts.utils.AccountEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountEncoder accountEncoder;

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public void save(Account account) {
        accountEncoder.encodePassword(account);
        accountRepository.save(account);
    }

    public boolean existsByUsername(String username) {
        return accountRepository.existsByUsername(username);
    }

    public boolean existsById(Long id) {
        return accountRepository.existsById(id);
    }

    public void deleteById(Long id) {
        accountRepository.deleteById(id);
    }
}
