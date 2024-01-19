package team01.studyCm.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomErrorCode {
  NOT_FOUND_EMAIL(HttpStatus.NOT_FOUND, "존재하지 않는 회원")

  ;


  private final HttpStatus httpStatus;
  private final String message;
}
