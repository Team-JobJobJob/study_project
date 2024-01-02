package team01.studyCm.chat.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.LastModifiedDate;

@ToString
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDto {

  private Long roomId;
  private String roomName;
  private String description;
  private Integer number;
  private String job;
  private LocalDateTime created_at;
  private LocalDateTime modified_at;
}
