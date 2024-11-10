package org.example.Controllers;

import lombok.AllArgsConstructor;
import org.example.DTOs.AccountDTO;
import org.example.Entities.Account;
import org.example.Services.AccountService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/account")
public class AccountController {

    private final AccountService accountService;

    @GetMapping()
    public ResponseEntity<Page<Account>> getAllAccounts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        var accounts = accountService.getAllAccounts(PageRequest.of(page, size));
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }
    @GetMapping(value = "/{accountId}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long accountId){
        var account = accountService.getAccountById(accountId);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }
    @PostMapping()
    public ResponseEntity<Account> createAccount(@RequestBody AccountDTO accountDTO){
        var account = accountService.createAccount(accountDTO);
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }
    @PatchMapping(value = "/{accountId}")
    public ResponseEntity<Account> updateAccount(@RequestBody AccountDTO accountDTO, @PathVariable Long accountId){
        var account = accountService.updateAccount(accountDTO, accountId);
        return new ResponseEntity<>(account, HttpStatus.ACCEPTED);
    }
    @DeleteMapping(value = "/{accountId}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long accountId){
        accountService.deleteAccount(accountId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
