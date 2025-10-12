package ru.yandex.practicum.bank.client.notification.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

/**
 * MailDto
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-15T00:23:17.622740+03:00[Europe/Moscow]", comments = "Generator version: 7.12.0")
public class MailDto {

  private String email;

  private String subject;

  private String text;

  public MailDto() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public MailDto(String email, String subject, String text) {
    this.email = email;
    this.subject = subject;
    this.text = text;
  }

  public MailDto email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Email получателя
   * @return email
   */
  @NotNull @Email
  @Schema(name = "email", example = "user@example.com", description = "Email получателя", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("email")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public MailDto subject(String subject) {
    this.subject = subject;
    return this;
  }

  /**
   * Тема письма
   * @return subject
   */
  @NotNull 
  @Schema(name = "subject", example = "Уведомление о платеже", description = "Тема письма", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("subject")
  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public MailDto text(String text) {
    this.text = text;
    return this;
  }

  /**
   * Текст письма
   * @return text
   */
  @NotNull 
  @Schema(name = "text", example = "Ваш перевод успешно выполнен.", description = "Текст письма", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("text")
  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MailDto mailDto = (MailDto) o;
    return Objects.equals(this.email, mailDto.email) &&
        Objects.equals(this.subject, mailDto.subject) &&
        Objects.equals(this.text, mailDto.text);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, subject, text);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MailDto {\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    subject: ").append(toIndentedString(subject)).append("\n");
    sb.append("    text: ").append(toIndentedString(text)).append("\n");
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

