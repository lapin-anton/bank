package ru.yandex.practicum.bank.service.exchange.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "exchange_rate", indexes = {
        @Index(name = "idx__exchange_rate_currency", columnList = "currency", unique = true)
})
@Getter
@Setter
public class ExchangeRate extends AbstractPersistable<Long> {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private Currency currency;

    @Column(nullable = false)
    private BigDecimal value = BigDecimal.ZERO;

    @CreationTimestamp
    private Instant created;

    @UpdateTimestamp
    private Instant updated;
}
