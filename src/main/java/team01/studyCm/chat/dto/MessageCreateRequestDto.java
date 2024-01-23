package team01.studyCm.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageCreateRequestDto {

  private Long chatId;
  private Long userId;
  private Long receiverId;

  private String sender;
  private String contents;

//  public MessageCreateRequestDto(Long chatId, Long userId, String sender, String contents) {
//    this.chatId = chatId;
//    this.userId = userId;
//    this.sender = sender;
//    this.contents = contents;
//  }
}
