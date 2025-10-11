package ru.yandex.practicum.bank.client.exchange.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * ConvertResponseDto
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-14T15:20:30.433277+03:00[Europe/Moscow]", comments = "Generator version: 7.12.0")
public class ConvertResponseDto {

  private BigDecimal result;

  public ConvertResponseDto() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ConvertResponseDto(BigDecimal result) {
    this.result = result;
  }

  public ConvertResponseDto result(BigDecimal result) {
    this.result = result;
    return this;
  }

  /**
   * Результат конверсии
   * @return result
   */
  @NotNull @Valid 
  @Schema(name = "result", example = "5000.00", description = "Результат конверсии", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("result")
  public BigDecimal getResult() {
    return result;
  }

  public void setResult(BigDecimal result) {
    this.result = result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConvertResponseDto convertResponseDto = (ConvertResponseDto) o;
    return Objects.equals(this.result, convertResponseDto.result);
  }

  @Override
  public int hashCode() {
    return Objects.hash(result);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConvertResponseDto {\n");
    sb.append("    result: ").append(toIndentedString(result)).append("\n");
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

