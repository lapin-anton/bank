package ru.yandex.practicum.bank.service.account.repostory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.bank.service.account.model.Currency;
import ru.yandex.practicum.bank.service.account.model.Account;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> getAllByUserId(String userId);

    Optional<Account> getByNumber(String number);

    Boolean existsByCurrencyAndUserId(Currency currency, String userId);
}
