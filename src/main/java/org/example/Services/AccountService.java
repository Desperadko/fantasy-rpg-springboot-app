package org.example.Services;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.example.DTOs.AccountDTO;
import org.example.Entities.Account;
import org.example.Mappers.AccountMapper;
import org.example.Repositories.AccountRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public Page<Account> getAllAccounts(Pageable pageable){
        return accountRepository.findAll(pageable);
    }
    public Account getAccountById(Long accountId) {
        return getAccount(accountId);
    }
    public Account createAccount(AccountDTO accountDTO) {
        var accountUsername = accountDTO.username();
        var accountEmail = accountDTO.email();

        var accountFoundByName = accountRepository.findAccountByUsername(accountUsername);
        var accountFoundByEmail = accountRepository.findAccountByEmail(accountEmail);

        if(accountFoundByName.isPresent())
            throw new IllegalStateException("Account with username '" + accountUsername + "' already exists..");
        if(accountFoundByEmail.isPresent())
            throw new IllegalStateException("Account with email '" + accountEmail + "' already exists..");

        return saveAccountDtoToDatabase(accountDTO, null);
    }
    public Account updateAccount(AccountDTO accountDTO, Long accountId){
        if(!accountRepository.existsById(accountId))
            throw new EntityNotFoundException("Account ID: " + accountId);
        return saveAccountDtoToDatabase(accountDTO, accountId);
    }
    public void deleteAccount(Long accountId){
        accountRepository.delete(getAccount(accountId));
    }

    //utils
    private Account getAccount(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account ID: " + accountId));
    }
    private Account saveAccountDtoToDatabase(AccountDTO accountDTO, Long accountId) {
        var account = accountMapper.convertDtoToEntity(accountDTO, accountId);
        return accountRepository.saveAndFlush(account);
    }
}



