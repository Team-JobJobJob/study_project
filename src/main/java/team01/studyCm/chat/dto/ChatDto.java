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
  private String chatName;
  private String description;
  private Integer memberCnt;
  private LocalDateTime created_at;
  private LocalDateTime modified_at;
  private String email;
  private String job;

  private Map<Long, String> userlist = new HashMap<>();

  public ChatDto(User loggedInUser) {
    this.email = loggedInUser.getEmail();
    this.job = loggedInUser.getJob();
    this.user = loggedInUser;
  }

}
