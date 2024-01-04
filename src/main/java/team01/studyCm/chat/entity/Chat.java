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
import team01.studyCm.chat.dto.ChatDto;
import team01.studyCm.user.entity.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Chat {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long chatId;
  @ManyToOne
  @JoinColumn(name = "user")
  private User user;
  @Column(length = 30, name = "chat_name")
  private String chatName;
  @Column
  private String description;
  @Column (name = "member_cnt")
  private Integer memberCnt;

  @Column
  private String email;

  @Column
  private String job;

  @CreatedDate
  @Column
  private LocalDateTime created_at;

  @LastModifiedDate
  @Column
  private LocalDateTime modified_at;

  public static Chat toSaveEntity(ChatDto chatDto) {
    return Chat.builder()
                  .chatName(chatDto.getChatName())
                  .user(chatDto.getUser())
                  .description(chatDto.getDescription())
                  .memberCnt(chatDto.getMemberCnt())
                  .created_at(LocalDateTime.now())
                  .modified_at(LocalDateTime.now())
                  .build();
  }

}
