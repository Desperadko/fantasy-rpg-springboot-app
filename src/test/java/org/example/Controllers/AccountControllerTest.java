package org.example.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.DTOs.AccountDTO;
import org.example.Repositories.AccountRepository;
import org.example.Services.AccountService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    void setup(){
        accountService.createAccount(
            new AccountDTO(
                 "Tin4o",
                 "tin4otin4ef@gmail.com",
                 "minahpomenijmunt"
            )
        );
        accountService.createAccount(
            new AccountDTO(
                    "Vik4o",
                    "vik4ovik4ef@gmail.com",
                    "azsampedal"
            )
        );
    }

    @AfterEach
    void cleanup(){
        jdbcTemplate.execute("DELETE FROM accounts");
        jdbcTemplate.execute("ALTER TABLE accounts ALTER COLUMN id RESTART WITH 1");
    }

    @Test
    void getAccountById_Success() throws Exception {
        mockMvc.perform(get("/account/{accountId}", 2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("Vik4o"));
    }

    @Test
    void createAccount_Success() throws Exception {
        var accountDTO = new AccountDTO(
                "JohnKantarStraik",
                "ezfrags@gmail.com",
                "ednoi6esbe6epodobra"
        );

        mockMvc.perform(post("/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("JohnKantarStraik"))
                .andExpect(jsonPath("$.email").value("ezfrags@gmail.com"))
                .andExpect(jsonPath("$.password").value("ednoi6esbe6epodobra"));
    }

    @Test
    void updateAccount_Success() throws Exception {
        var accountDTO = new AccountDTO(
                "Biktor",
                "vik4odebelakovvik4ef@gmail.com",
                "vseo6tesampedal"
        );

        mockMvc.perform(patch("/account/{accountId}", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountDTO)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.username").value("Biktor"))
                .andExpect(jsonPath("$.email").value("vik4odebelakovvik4ef@gmail.com"))
                .andExpect(jsonPath("$.password").value("vseo6tesampedal"));
    }

    @Test
    void deleteAccount_Success() throws Exception {
        mockMvc.perform(delete("/account/{accountId}", 1))
                .andExpect(status().isAccepted());

        var deletedAccount = accountRepository.findById(1L);
        assertThat(deletedAccount).isEmpty();
    }
}