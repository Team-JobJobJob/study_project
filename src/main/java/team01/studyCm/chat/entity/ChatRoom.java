package team01.studyCm.chat.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import team01.studyCm.chat.dto.ChatRoomDto;
import team01.studyCm.user.entity.User;

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
  @ManyToOne
  private User user;
  @Column(length = 30, name = "room_name")
  private String roomName;
  @Column
  private String description;
  @Column
  private Integer number;

  @CreatedDate
  @Column
  private LocalDateTime created_at;

  @LastModifiedDate
  @Column
  private LocalDateTime modified_at;

  public static ChatRoom toSaveEntity(ChatRoomDto chatRoomDto) {
    return ChatRoom.builder()
                  .roomName(chatRoomDto.getRoomName())
                  .user(chatRoomDto.getUser())
                  .description(chatRoomDto.getDescription())
                  .number(chatRoomDto.getNumber())
                  .created_at(LocalDateTime.now())
                  .modified_at(LocalDateTime.now())
                  .build();
  }

}
