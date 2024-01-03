package team01.studyCm.chat.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDto {

  private Long roomId;
  private String roomName;
  private String description;
  private Integer number;
  private LocalDateTime created_at;
  private LocalDateTime modified_at;
}
