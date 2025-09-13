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
 * TransferCheckDto
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-14T22:59:57.291682+03:00[Europe/Moscow]", comments = "Generator version: 7.12.0")
public class TransferCheckDto {

  private String fromAccount;

  private String toAccount;

  private BigDecimal amount;

  public TransferCheckDto() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public TransferCheckDto(String fromAccount, String toAccount, BigDecimal amount) {
    this.fromAccount = fromAccount;
    this.toAccount = toAccount;
    this.amount = amount;
  }

  public TransferCheckDto fromAccount(String fromAccount) {
    this.fromAccount = fromAccount;
    return this;
  }

  /**
   * Счёт списания
   * 
   * @return fromAccount
   */
  @NotNull
  @Schema(name = "fromAccount", example = "40817810099910001111", description = "Счёт списания", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("fromAccount")
  public String getFromAccount() {
    return fromAccount;
  }

  public void setFromAccount(String fromAccount) {
    this.fromAccount = fromAccount;
  }

  public TransferCheckDto toAccount(String toAccount) {
    this.toAccount = toAccount;
    return this;
  }

  /**
   * Счёт зачисления
   * 
   * @return toAccount
   */
  @NotNull
  @Schema(name = "toAccount", example = "40817810099910002222", description = "Счёт зачисления", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("toAccount")
  public String getToAccount() {
    return toAccount;
  }

  public void setToAccount(String toAccount) {
    this.toAccount = toAccount;
  }

  public TransferCheckDto amount(BigDecimal amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Сумма перевода
   * 
   * @return amount
   */
  @NotNull
  @Valid
  @Schema(name = "amount", example = "1500.00", description = "Сумма перевода", requiredMode = Schema.RequiredMode.REQUIRED)
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
    TransferCheckDto transferCheckDto = (TransferCheckDto) o;
    return Objects.equals(this.fromAccount, transferCheckDto.fromAccount) &&
        Objects.equals(this.toAccount, transferCheckDto.toAccount) &&
        Objects.equals(this.amount, transferCheckDto.amount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fromAccount, toAccount, amount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TransferCheckDto {\n");
    sb.append("    fromAccount: ").append(toIndentedString(fromAccount)).append("\n");
    sb.append("    toAccount: ").append(toIndentedString(toAccount)).append("\n");
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
