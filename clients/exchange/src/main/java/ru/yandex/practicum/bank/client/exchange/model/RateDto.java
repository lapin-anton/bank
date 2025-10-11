package ru.yandex.practicum.bank.client.exchange.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * RateDto
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-14T15:20:30.433277+03:00[Europe/Moscow]", comments = "Generator version: 7.12.0")
public class RateDto {

  private Currency currency;

  private BigDecimal value;

  public RateDto() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public RateDto(Currency currency, BigDecimal value) {
    this.currency = currency;
    this.value = value;
  }

  public RateDto currency(Currency currency) {
    this.currency = currency;
    return this;
  }

  /**
   * Get currency
   * @return currency
   */
  @NotNull @Valid 
  @Schema(name = "currency", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("currency")
  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }

  public RateDto value(BigDecimal value) {
    this.value = value;
    return this;
  }

  /**
   * Курс по отношению к базовой валюте (RUB)
   * @return value
   */
  @NotNull @Valid 
  @Schema(name = "value", example = "5000.00", description = "Курс по отношению к базовой валюте (RUB)", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("value")
  public BigDecimal getValue() {
    return value;
  }

  public void setValue(BigDecimal value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RateDto rateDto = (RateDto) o;
    return Objects.equals(this.currency, rateDto.currency) &&
        Objects.equals(this.value, rateDto.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(currency, value);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RateDto {\n");
    sb.append("    currency: ").append(toIndentedString(currency)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
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

