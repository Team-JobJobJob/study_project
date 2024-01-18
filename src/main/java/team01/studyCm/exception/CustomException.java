package team01.studyCm.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {

  private CustomErrorCode errorCode;
  public CustomException(CustomErrorCode errorCode) {
    this.errorCode = errorCode;
  }

}
