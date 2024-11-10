package org.example.Repositories;

import org.example.Entities.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Override
    Page<Account> findAll(Pageable pageable);
    Optional<Account> findAccountByUsername(String username);
    Optional<Account> findAccountByEmail(String email);
}
