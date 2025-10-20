package ru.yandex.practicum.bank.client.account.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.lang.Nullable;
import ru.yandex.practicum.bank.client.account.model.Currency;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * OpenAccountDto
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-13T22:41:27.854509+03:00[Europe/Moscow]", comments = "Generator version: 7.12.0")
public class OpenAccountDto {

  private Currency currency;

  public OpenAccountDto() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public OpenAccountDto(Currency currency) {
    this.currency = currency;
  }

  public OpenAccountDto currency(Currency currency) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OpenAccountDto openAccountDto = (OpenAccountDto) o;
    return Objects.equals(this.currency, openAccountDto.currency);
  }

  @Override
  public int hashCode() {
    return Objects.hash(currency);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OpenAccountDto {\n");
    sb.append("    currency: ").append(toIndentedString(currency)).append("\n");
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

