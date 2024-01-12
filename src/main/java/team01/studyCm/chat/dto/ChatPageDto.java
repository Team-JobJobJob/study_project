package team01.studyCm.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team01.studyCm.chat.entity.Chat;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatPageDto {
  private Long chatId;
  private String chatName;
  private String description;
  private Integer memberCnt;
  private String job;

  public ChatPageDto(Chat chat){
    this.chatId = chat.getChatId();
    this.chatName = chat.getChatName();
    this.description = chat.getDescription();
    this.memberCnt =chat.getMemberCnt();
    this.job = chat.getJob();
  }

}
