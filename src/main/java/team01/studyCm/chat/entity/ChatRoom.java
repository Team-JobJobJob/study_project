package team01.studyCm.chat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import team01.studyCm.chat.dto.ChatRoomDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ChatRoom {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long roomId;
  @Column
  private String roomName;
  @Column
  private String description;
  @Column
  private Integer number;
  @Column
  private String job;

  @CreatedDate
  private LocalDateTime created_at;

  @LastModifiedDate
  private LocalDateTime modified_at;

  public static ChatRoom toSaveEntity(ChatRoomDto chatRoomDto) {
    return ChatRoom.builder()
        .roomName(chatRoomDto.getRoomName())
        .description(chatRoomDto.getDescription())
        .number(chatRoomDto.getNumber())
        .job(chatRoomDto.getJob())
        .build();
  }

}
