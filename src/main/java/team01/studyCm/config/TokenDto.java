package team01.studyCm.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import team01.studyCm.user.dto.LoginCredDto;
import team01.studyCm.user.dto.UserDto;

@Builder
@Data
@AllArgsConstructor
public class TokenDto {
  private String accessToken;
  private String refreshToken;
  private UserDto userDto;

}
