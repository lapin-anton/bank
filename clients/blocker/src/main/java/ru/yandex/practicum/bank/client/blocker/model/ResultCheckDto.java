package ru.yandex.practicum.bank.client.blocker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

/**
 * ResultCheckDto
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-14T22:59:57.291682+03:00[Europe/Moscow]", comments = "Generator version: 7.12.0")
public class ResultCheckDto {

  private Boolean result;

  public ResultCheckDto() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ResultCheckDto(Boolean result) {
    this.result = result;
  }

  public ResultCheckDto result(Boolean result) {
    this.result = result;
    return this;
  }

  /**
   * Результат проверки (true — операция разрешена)
   * @return result
   */
  @NotNull 
  @Schema(name = "result", example = "true", description = "Результат проверки (true — операция разрешена)", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("result")
  public Boolean getResult() {
    return result;
  }

  public void setResult(Boolean result) {
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
    ResultCheckDto resultCheckDto = (ResultCheckDto) o;
    return Objects.equals(this.result, resultCheckDto.result);
  }

  @Override
  public int hashCode() {
    return Objects.hash(result);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ResultCheckDto {\n");
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

