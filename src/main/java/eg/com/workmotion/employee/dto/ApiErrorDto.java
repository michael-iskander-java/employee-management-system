package eg.com.workmotion.employee.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class ApiErrorDto {

  private HttpStatus status;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private LocalDateTime timestamp;
  private String message;
  private String debugMessage;
  private StackTraceElement[] subErrors;

  private ApiErrorDto() {
    timestamp = LocalDateTime.now();
  }

  public ApiErrorDto(HttpStatus status, String message, String debugMessage) {
    this();
    this.status = status;
    this.message = message;
    this.debugMessage = debugMessage;
  }

  public ApiErrorDto(HttpStatus status, String message) {
    this();
    this.status = status;
    this.message = message;
  }

  public ApiErrorDto(HttpStatus status, String message, StackTraceElement[] subErrors) {
    this();
    this.status = status;
    this.message = message;
    this.subErrors = subErrors;
  }
}
