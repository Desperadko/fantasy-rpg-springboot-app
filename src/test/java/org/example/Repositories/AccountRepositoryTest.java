package org.example.Repositories;

import org.example.Entities.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void saveAccount_Success(){
        var account = new Account();
        account.setUsername("Tin4o");
        account.setEmail("tin4otin4ef@gmail.com");
        account.setPassword("JohnSpringboot1488");

        var savedAccount = accountRepository.save(account);

        assertThat(savedAccount.getId()).isNotNull();
        assertThat(savedAccount.getUsername()).isEqualTo("Tin4o");
    }

    @Test
    void findAccountByEmail_Success(){
        var account = new Account();
        account.setUsername("Vik4o");
        account.setEmail("vik4ovik4ef@gmail.com");
        account.setPassword("intellijh8er");
        accountRepository.save(account);

        var foundAccount = accountRepository.findAccountByEmail("vik4ovik4ef@gmail.com");

        assertThat(foundAccount).isPresent();
        assertThat(foundAccount.get().getUsername()).isEqualTo("Vik4o");
    }

    @Test
    void deleteAccount_Success(){
        var account = new Account();
        account.setUsername("JohnDarkSouls");
        account.setEmail("johndarksouls@gmail.com");
        account.setPassword("gearlessrunftw");
        var savedAccount = accountRepository.save(account);

        accountRepository.delete(savedAccount);

        var deletedAccount = accountRepository.findById(savedAccount.getId());
        assertThat(deletedAccount).isEmpty();
    }
}