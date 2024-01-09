package team01.studyCm.chat.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.web.socket.WebSocketSession;
import team01.studyCm.chat.dto.ChatDto;
import team01.studyCm.chat.service.ChatService;
import team01.studyCm.user.entity.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "chat")
public class Chat {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "chat_id")
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

  @Column (name = "user_cnt")
  private Long userCnt;

  @Column
  private String job;

  @CreatedDate
  @Column
  private LocalDateTime created_at;

  @LastModifiedDate
  @Column
  private LocalDateTime modified_at;

  @ElementCollection
  @CollectionTable(
          name = "user_list"
  )
  @MapKeyColumn(name="user_id")
  @Column(name="user_name")
  private Map<Long,String> userList = new HashMap<>();





  public static Chat toEntity(ChatDto chatDto) {
    return Chat.builder()
                  .chatName(chatDto.getChatName())
                  .user(chatDto.getUser())
                  .description(chatDto.getDescription())
                  .memberCnt(chatDto.getMemberCnt())
                  .created_at(LocalDateTime.now())
                  .modified_at(LocalDateTime.now())
                  .build();
  }


  public static Chat toEntity(ChatDto chatDto, User loggedInUser) {
    return Chat.builder()
            .chatName(chatDto.getChatName())
            .description(chatDto.getDescription())
            .memberCnt(chatDto.getMemberCnt())
            .job(loggedInUser.getJob())
            .user(loggedInUser)
            .created_at(LocalDateTime.now())
            .modified_at(LocalDateTime.now())
            .build();
  }

  // 채팅 관련 메소드

}
