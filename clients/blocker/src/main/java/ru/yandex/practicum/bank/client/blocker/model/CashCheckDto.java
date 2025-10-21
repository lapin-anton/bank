package ru.yandex.practicum.bank.client.blocker.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.math.BigDecimal;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * CashCheckDto
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-14T22:59:57.291682+03:00[Europe/Moscow]", comments = "Generator version: 7.12.0")
public class CashCheckDto {

  private String accountNumber;

  private BigDecimal amount;

  public CashCheckDto() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CashCheckDto(String accountNumber, BigDecimal amount) {
    this.accountNumber = accountNumber;
    this.amount = amount;
  }

  public CashCheckDto accountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
    return this;
  }

  /**
   * Номер счёта для проверки
   * @return accountNumber
   */
  @NotNull 
  @Schema(name = "accountNumber", example = "40817810099910001111", description = "Номер счёта для проверки", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("accountNumber")
  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public CashCheckDto amount(BigDecimal amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Сумма операции
   * @return amount
   */
  @NotNull @Valid 
  @Schema(name = "amount", example = "1000.00", description = "Сумма операции", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("amount")
  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CashCheckDto cashCheckDto = (CashCheckDto) o;
    return Objects.equals(this.accountNumber, cashCheckDto.accountNumber) &&
        Objects.equals(this.amount, cashCheckDto.amount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountNumber, amount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CashCheckDto {\n");
    sb.append("    accountNumber: ").append(toIndentedString(accountNumber)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
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

