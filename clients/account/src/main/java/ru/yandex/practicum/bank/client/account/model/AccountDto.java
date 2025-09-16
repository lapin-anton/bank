package ru.yandex.practicum.bank.client.account.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.math.BigDecimal;
import org.springframework.lang.Nullable;
import ru.yandex.practicum.bank.client.account.model.AccountStatus;
import ru.yandex.practicum.bank.client.account.model.Currency;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.*;
import jakarta.annotation.Generated;

/**
 * AccountDto
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-13T22:41:27.854509+03:00[Europe/Moscow]", comments = "Generator version: 7.12.0")
public class AccountDto {

  private @Nullable Long id;

  private @Nullable String number;

  private @Nullable String userId;

  private @Nullable BigDecimal balance;

  private @Nullable AccountStatus status;

  private @Nullable Currency currency;

  private @Nullable Long version;

  public AccountDto id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * 
   * @return id
   */

  @Schema(name = "id", example = "1001", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public AccountDto number(String number) {
    this.number = number;
    return this;
  }

  /**
   * Get number
   * 
   * @return number
   */

  @Schema(name = "number", example = "40817810099910004321", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("number")
  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public AccountDto userId(String userId) {
    this.userId = userId;
    return this;
  }

  /**
   * Get userId
   * 
   * @return userId
   */

  @Schema(name = "userId", example = "user-123", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("userId")
  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public AccountDto balance(BigDecimal balance) {
    this.balance = balance;
    return this;
  }

  /**
   * Сумма на счете (BigDecimal)
   * 
   * @return balance
   */
  @Valid
  @Schema(name = "balance", example = "5000.00", description = "Сумма на счете (BigDecimal)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("balance")
  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public AccountDto status(AccountStatus status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * 
   * @return status
   */
  @Valid
  @Schema(name = "status", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("status")
  public AccountStatus getStatus() {
    return status;
  }

  public void setStatus(AccountStatus status) {
    this.status = status;
  }

  public AccountDto currency(Currency currency) {
    this.currency = currency;
    return this;
  }

  /**
   * Get currency
   * 
   * @return currency
   */
  @Valid
  @Schema(name = "currency", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("currency")
  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }

  public AccountDto version(Long version) {
    this.version = version;
    return this;
  }

  /**
   * Get version
   * 
   * @return version
   */

  @Schema(name = "version", example = "5", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("version")
  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AccountDto accountDto = (AccountDto) o;
    return Objects.equals(this.id, accountDto.id) &&
        Objects.equals(this.number, accountDto.number) &&
        Objects.equals(this.userId, accountDto.userId) &&
        Objects.equals(this.balance, accountDto.balance) &&
        Objects.equals(this.status, accountDto.status) &&
        Objects.equals(this.currency, accountDto.currency) &&
        Objects.equals(this.version, accountDto.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, number, userId, balance, status, currency, version);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AccountDto {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    number: ").append(toIndentedString(number)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    balance: ").append(toIndentedString(balance)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    currency: ").append(toIndentedString(currency)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
