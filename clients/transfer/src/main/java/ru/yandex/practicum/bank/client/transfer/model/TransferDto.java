package ru.yandex.practicum.bank.client.transfer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * TransferDto
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-14T20:54:57.348613+03:00[Europe/Moscow]", comments = "Generator version: 7.12.0")
public class TransferDto {

  private String fromAccount;

  private String toAccount;

  private BigDecimal amount;

  public TransferDto() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public TransferDto(String fromAccount, String toAccount, BigDecimal amount) {
    this.fromAccount = fromAccount;
    this.toAccount = toAccount;
    this.amount = amount;
  }

  public TransferDto fromAccount(String fromAccount) {
    this.fromAccount = fromAccount;
    return this;
  }

  /**
   * Номер счёта списания
   * @return fromAccount
   */
  @NotNull 
  @Schema(name = "fromAccount", example = "40817810099910001111", description = "Номер счёта списания", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("fromAccount")
  public String getFromAccount() {
    return fromAccount;
  }

  public void setFromAccount(String fromAccount) {
    this.fromAccount = fromAccount;
  }

  public TransferDto toAccount(String toAccount) {
    this.toAccount = toAccount;
    return this;
  }

  /**
   * Номер счёта зачисления
   * @return toAccount
   */
  @NotNull 
  @Schema(name = "toAccount", example = "40817810099910002222", description = "Номер счёта зачисления", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("toAccount")
  public String getToAccount() {
    return toAccount;
  }

  public void setToAccount(String toAccount) {
    this.toAccount = toAccount;
  }

  public TransferDto amount(BigDecimal amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Сумма перевода (BigDecimal)
   * @return amount
   */
  @NotNull @Valid 
  @Schema(name = "amount", example = "5000.00", description = "Сумма перевода (BigDecimal)", requiredMode = Schema.RequiredMode.REQUIRED)
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
    TransferDto transferDto = (TransferDto) o;
    return Objects.equals(this.fromAccount, transferDto.fromAccount) &&
        Objects.equals(this.toAccount, transferDto.toAccount) &&
        Objects.equals(this.amount, transferDto.amount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fromAccount, toAccount, amount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TransferDto {\n");
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

