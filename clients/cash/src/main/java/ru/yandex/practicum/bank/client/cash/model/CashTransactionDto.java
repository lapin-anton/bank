package ru.yandex.practicum.bank.client.cash.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * CashTransactionDto
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-13T23:13:29.470866+03:00[Europe/Moscow]", comments = "Generator version: 7.12.0")
public class CashTransactionDto {

  private String accountNumber;

  private BigDecimal amount;

  public CashTransactionDto() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CashTransactionDto(String accountNumber, BigDecimal amount) {
    this.accountNumber = accountNumber;
    this.amount = amount;
  }

  public CashTransactionDto accountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
    return this;
  }

  /**
   * Номер счёта для операции
   * @return accountNumber
   */
  @NotNull 
  @Schema(name = "accountNumber", example = "40817810099910004321", description = "Номер счёта для операции", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("accountNumber")
  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public CashTransactionDto amount(BigDecimal amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Сумма операции (BigDecimal)
   * @return amount
   */
  @NotNull @Valid 
  @Schema(name = "amount", example = "2500.00", description = "Сумма операции (BigDecimal)", requiredMode = Schema.RequiredMode.REQUIRED)
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
    CashTransactionDto cashTransactionDto = (CashTransactionDto) o;
    return Objects.equals(this.accountNumber, cashTransactionDto.accountNumber) &&
        Objects.equals(this.amount, cashTransactionDto.amount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountNumber, amount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CashTransactionDto {\n");
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

