package ru.yandex.practicum.bank.client.exchange.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * ConvertRequestDto
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-14T15:20:30.433277+03:00[Europe/Moscow]", comments = "Generator version: 7.12.0")
public class ConvertRequestDto {

  private Currency fromCurrency;

  private Currency toCurrency;

  private BigDecimal amount;

  public ConvertRequestDto() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ConvertRequestDto(Currency fromCurrency, Currency toCurrency, BigDecimal amount) {
    this.fromCurrency = fromCurrency;
    this.toCurrency = toCurrency;
    this.amount = amount;
  }

  public ConvertRequestDto fromCurrency(Currency fromCurrency) {
    this.fromCurrency = fromCurrency;
    return this;
  }

  /**
   * Get fromCurrency
   * @return fromCurrency
   */
  @NotNull @Valid 
  @Schema(name = "fromCurrency", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("fromCurrency")
  public Currency getFromCurrency() {
    return fromCurrency;
  }

  public void setFromCurrency(Currency fromCurrency) {
    this.fromCurrency = fromCurrency;
  }

  public ConvertRequestDto toCurrency(Currency toCurrency) {
    this.toCurrency = toCurrency;
    return this;
  }

  /**
   * Get toCurrency
   * @return toCurrency
   */
  @NotNull @Valid 
  @Schema(name = "toCurrency", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("toCurrency")
  public Currency getToCurrency() {
    return toCurrency;
  }

  public void setToCurrency(Currency toCurrency) {
    this.toCurrency = toCurrency;
  }

  public ConvertRequestDto amount(BigDecimal amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Сумма для конвертации
   * @return amount
   */
  @NotNull @Valid 
  @Schema(name = "amount", example = "5000.00", description = "Сумма для конвертации", requiredMode = Schema.RequiredMode.REQUIRED)
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
    ConvertRequestDto convertRequestDto = (ConvertRequestDto) o;
    return Objects.equals(this.fromCurrency, convertRequestDto.fromCurrency) &&
        Objects.equals(this.toCurrency, convertRequestDto.toCurrency) &&
        Objects.equals(this.amount, convertRequestDto.amount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fromCurrency, toCurrency, amount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConvertRequestDto {\n");
    sb.append("    fromCurrency: ").append(toIndentedString(fromCurrency)).append("\n");
    sb.append("    toCurrency: ").append(toIndentedString(toCurrency)).append("\n");
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

