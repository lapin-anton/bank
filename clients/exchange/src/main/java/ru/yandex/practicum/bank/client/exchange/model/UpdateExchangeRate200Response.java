package ru.yandex.practicum.bank.client.exchange.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.*;
import jakarta.annotation.Generated;

/**
 * UpdateExchangeRate200Response
 */

@JsonTypeName("updateExchangeRate_200_response")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-14T15:20:30.433277+03:00[Europe/Moscow]", comments = "Generator version: 7.12.0")
public class UpdateExchangeRate200Response {

  private @Nullable String status;

  public UpdateExchangeRate200Response status(String status) {
    this.status = status;
    return this;
  }

  /**
   * Статус операции
   * 
   * @return status
   */

  @Schema(name = "status", example = "OK", description = "Статус операции", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("status")
  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateExchangeRate200Response updateExchangeRate200Response = (UpdateExchangeRate200Response) o;
    return Objects.equals(this.status, updateExchangeRate200Response.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateExchangeRate200Response {\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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
