package team01.studyCm.chat.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team01.studyCm.user.entity.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {
    // 메시지  타입 : 입장, 채팅
    // 메시지 타입에 따라서 동작하는 구조가 달라진다.
    // 입장과 퇴장 ENTER 과 LEAVE 의 경우 입장/퇴장 이벤트 처리가 실행되고,
    // TALK 는 말 그대로 내용이 해당 채팅방을 SUB 하고 있는 모든 클라이언트에게 전달된다.
    public enum MessageType{
        ENTER, TALK, LEAVE;
    }

    private MessageType type; // 메시지 타입
    private Long chatId; // 방 번호
    private Long userId; // 유저 번호
    private String sender; // 채팅을 보낸 사람
    private Long receiverId;



    private String contents; // 메시지
    private String time; // 채팅 발송 시간

}
