package ru.yandex.practicum.bank.service.account.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "account", uniqueConstraints = {
                @UniqueConstraint(name = "idx__account_number", columnNames = "number"),
                @UniqueConstraint(name = "idx__account_user_currency", columnNames = { "user_id", "currency" })
}, indexes = {
                @Index(name = "idx__account_user_id", columnList = "user_id")
})
@Getter
@Setter
public class Account extends AbstractPersistable<Long> {

        @Column(name = "number", nullable = false, unique = true)
        private String number;

        @Column(name = "user_id", nullable = false)
        private String userId;

        @Column(nullable = false)
        private BigDecimal balance = BigDecimal.ZERO;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private AccountStatus status = AccountStatus.ACTIVE;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private Currency currency;

        @Version
        private Long version;

        @CreationTimestamp
        private Instant created;

        @UpdateTimestamp
        private Instant updated;

        public void setTestId(Long id) {
                this.setId(id);
        }
}
