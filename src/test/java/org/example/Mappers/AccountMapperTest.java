package org.example.Mappers;

import org.example.DTOs.AccountDTO;
import org.example.Entities.Account;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AccountMapperTest {

    private final AccountMapper accountMapper = Mappers.getMapper(AccountMapper.class);

    @Test
    void convertDtoToEntity_Success() {
        AccountDTO accountDTO = new AccountDTO(
                "Tin4o",
                "tin4otin4ef@gmail.com",
                "bestmageeuneduud"
        );

        var accountId = 1L;

        Account account = accountMapper.convertDtoToEntity(accountDTO, accountId);

        assertThat(account).isNotNull();
        assertThat(account.getUsername()).isEqualTo("Tin4o");
        assertThat(account.getEmail()).isEqualTo("tin4otin4ef@gmail.com");
        assertThat(account.getPassword()).isEqualTo("bestmageeuneduud");
        assertThat(account.getId()).isEqualTo(1L);
    }

    @Test
    void convertEntityToDto_Success() {
        Account account = new Account();
        account.setUsername("Vik4o");
        account.setEmail("vik4ovik4ef@gmail.com");
        account.setPassword("nqqdacukamhiilarweguze");
        account.setId(2L);

        AccountDTO accountDTO = accountMapper.convertEntityToDto(account);

        assertThat(accountDTO).isNotNull();
        assertThat(accountDTO.username()).isEqualTo("Vik4o");
        assertThat(accountDTO.email()).isEqualTo("vik4ovik4ef@gmail.com");
        assertThat(accountDTO.password()).isEqualTo("nqqdacukamhiilarweguze");
    }
}