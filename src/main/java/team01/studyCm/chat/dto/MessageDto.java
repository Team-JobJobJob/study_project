package team01.studyCm.chat.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import team01.studyCm.chat.entity.Message;
import team01.studyCm.user.entity.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDto {
    // 메시지  타입 : 입장, 채팅
    // 메시지 타입에 따라서 동작하는 구조가 달라진다.
    // 입장과 퇴장 ENTER 과 LEAVE 의 경우 입장/퇴장 이벤트 처리가 실행되고,
    // TALK 는 말 그대로 내용이 해당 채팅방을 SUB 하고 있는 모든 클라이언트에게 전달된다.
    public enum MessageType{
        ENTER, TALK, LEAVE;
    }

    private MessageType type; // 메시지 타입
    private Long id;
    private Long chatId; // 방 번호
    private Long userId; // 유저 번호
    private String sender; // 채팅을 보낸 사람
    private Long receiverId;


    private String contents; // 메시지
    private String createdAt; // 채팅 발송 시간


//    public static MessageDto toMessageDto(Message message){
//        MessageDto messageDto = new MessageDto();
//
//        messageDto.setId(message.getId());
//
//        messageDto.setChatId(message.getChat().getChatId());
//        messageDto.setUserId(message.getUser().getUser_id());
//
//        messageDto.setSender(message.getSender());
//        messageDto.setContents(message.getContents());
//
//        return messageDto;
//
//    }


}
