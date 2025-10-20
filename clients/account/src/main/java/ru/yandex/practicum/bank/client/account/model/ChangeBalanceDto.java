package ru.yandex.practicum.bank.client.account.model;

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
 * ChangeBalanceDto
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-13T22:41:27.854509+03:00[Europe/Moscow]", comments = "Generator version: 7.12.0")
public class ChangeBalanceDto {

  private BigDecimal amount;

  private Long version;

  public ChangeBalanceDto() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ChangeBalanceDto(BigDecimal amount, Long version) {
    this.amount = amount;
    this.version = version;
  }

  public ChangeBalanceDto amount(BigDecimal amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Сумма изменения баланса (BigDecimal)
   * @return amount
   */
  @NotNull @Valid 
  @Schema(name = "amount", example = "5000.00", description = "Сумма изменения баланса (BigDecimal)", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("amount")
  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public ChangeBalanceDto version(Long version) {
    this.version = version;
    return this;
  }

  /**
   * Get version
   * @return version
   */
  @NotNull 
  @Schema(name = "version", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
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
    ChangeBalanceDto changeBalanceDto = (ChangeBalanceDto) o;
    return Objects.equals(this.amount, changeBalanceDto.amount) &&
        Objects.equals(this.version, changeBalanceDto.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(amount, version);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ChangeBalanceDto {\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
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

