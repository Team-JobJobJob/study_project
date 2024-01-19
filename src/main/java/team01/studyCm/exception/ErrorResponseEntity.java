package team01.studyCm.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

@Builder
@Getter
@Setter
public class ErrorResponseEntity {

  private int status;
  private String code;
  private String message;

  public static ResponseEntity<ErrorResponseEntity> toResponseEntity(CustomErrorCode e){
    return ResponseEntity
        .status(e.getHttpStatus())
        .body(ErrorResponseEntity.builder()
            .status(e.getHttpStatus().value())
            .code(e.name())
            .message(e.getMessage())
            .build());
  }

}
