package ru.yandex.practicum.bank.service.account.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.bank.service.account.model.Account;
import ru.yandex.practicum.bank.service.account.model.AccountStatus;
import ru.yandex.practicum.bank.service.account.model.Currency;
import ru.yandex.practicum.bank.service.account.repostory.AccountRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class AccountRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    @DisplayName("should save and find account by ID and number")
    void saveAndFindAccount() {
        Account account = createAccount("user-1", "ACC-123", Currency.USD);

        Account saved = accountRepository.save(account);

        Optional<Account> byNumber = accountRepository.getByNumber("ACC-123");
        Optional<Account> byId = accountRepository.findById(saved.getId());

        assertThat(byNumber).isPresent();
        assertThat(byId).isPresent();
        assertThat(byNumber.get().getId()).isEqualTo(saved.getId());
    }

    @Test
    @DisplayName("should return all accounts by userId")
    void getAllByUserId() {
        Account acc1 = createAccount("user-1", "ACC-001", Currency.RUB);
        Account acc2 = createAccount("user-1", "ACC-002", Currency.EUR);
        Account acc3 = createAccount("user-2", "ACC-003", Currency.USD);

        accountRepository.saveAll(List.of(acc1, acc2, acc3));

        List<Account> accounts = accountRepository.getAllByUserId("user-1");

        assertThat(accounts).hasSize(2)
                .allMatch(acc -> acc.getUserId().equals("user-1"));
    }

    @Test
    @DisplayName("should check if currency exists for user")
    void existsByCurrencyAndUserId() {
        accountRepository.save(createAccount("user-3", "ACC-100", Currency.EUR));

        Boolean exists = accountRepository.existsByCurrencyAndUserId(Currency.EUR, "user-3");
        Boolean notExists = accountRepository.existsByCurrencyAndUserId(Currency.USD, "user-3");

        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();
    }

    private Account createAccount(String userId, String number, Currency currency) {
        Account account = new Account();
        account.setUserId(userId);
        account.setNumber(number);
        account.setCurrency(currency);
        account.setBalance(BigDecimal.ZERO);
        account.setStatus(AccountStatus.ACTIVE);
        account.setVersion(0L);
        return account;
    }
}
