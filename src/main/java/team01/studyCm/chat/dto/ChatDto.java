package team01.studyCm.chat.dto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import team01.studyCm.user.entity.User;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatDto {

  private Long chatId;
  private User user;
  private String roomName;
  private String description;
  private Integer number;
  private LocalDateTime created_at;
  private LocalDateTime modified_at;
  private String email;
  private String job;

  public Map<String, Object> getOtherDtoFields() {
    Map<String, Object> otherDtoFields = new HashMap<>();
    otherDtoFields.put("email", user.getEmail());
    otherDtoFields.put("job", user.getJob());
    // 필요에 따라 추가
    return otherDtoFields;
  }
}
