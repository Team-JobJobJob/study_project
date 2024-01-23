package team01.studyCm.chat.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import team01.studyCm.chat.dto.MessageCreateRequestDto;
import team01.studyCm.chat.dto.MessageDto;
import team01.studyCm.user.entity.User;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private MessageDto.MessageType type; // 메시지 타입

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chat_id")
    private Chat chat; // 방 번호

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user; // 유저 번호

    private String sender; // 채팅을 보낸 사람

    private Long receiverId;

    private String contents; // 메시지

    @CreatedDate
    private LocalDateTime createdAt; // 채팅 발송 시간

    @Builder
    public Message(Chat chat, String sender, String contents) {
        this.chat = chat;
        this.sender = sender;
        this.contents = contents;
        this.createdAt = LocalDateTime.now();
    }

    public static Message createMessage(Chat chat, String sender, String contents) {
        return Message.builder()
                .chat(chat)
                .sender(sender)
                .contents(contents)
                .build();
    }

}
