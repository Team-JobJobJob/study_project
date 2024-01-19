package team01.studyCm.chat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import team01.studyCm.auth.CustomOAuth2User;
import team01.studyCm.chat.dto.MessageCreateRequestDto;
import team01.studyCm.chat.dto.MessageDto;
import team01.studyCm.chat.entity.Chat;
import team01.studyCm.chat.repository.ChatRepository;
import team01.studyCm.chat.service.ChatService;
import team01.studyCm.config.TokenProvider;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MessageController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final TokenProvider tokenProvider;
    private final ChatService chatService;



//    @MessageMapping(value = "/chat/enter")
//    public void enter(MessageDto messageDto){
//        if (MessageDto.MessageType.ENTER.equals(messageDto.getType())) {
//            messageDto.setContents(messageDto.getSender() + "님이 채팅방에 참여하였습니다.");
//        }
//        messagingTemplate.convertAndSend("/sub/chat/room/" + messageDto.getChatId(), messageDto);
//
//    }


    @MessageMapping("/chat/message/{chatId}")
    @SendTo("/chat/room/{chatId}")
    public void contents(@DestinationVariable Long chatId, MessageDto messageDto, CustomOAuth2User oAuth2User) {
        String loginId = tokenProvider.createAccessToken(oAuth2User.getEmail());
        if (MessageDto.MessageType.ENTER.equals(messageDto.getType())) {
            messageDto.setContents(messageDto.getSender() + "님이 채팅방에 참여하였습니다.");
        }
        messagingTemplate.convertAndSend("/sub/chat/room/" + messageDto.getChatId(), messageDto);

        // DB에 채팅내용 저장
//        Chat chat= crr.findByChatId(messageDto.getChatId());
//        MessageCreateRequestDto chatMessageSaveDTO = new MessageCreateRequestDto(messageDto.getChatId(),messageDto.getSender(), messageDto.getContents());
//        cr.save(Message.toMessage(chatMessageSaveDTO,chat));
    }

    // 채팅에 참여한 유저 리스트 반환
//    @GetMapping("/chat/userlist")
//    @ResponseBody
//    public ArrayList<String> userList(Long chatId) {
//
//        return chatService.getUserList(chatId);
//    }

}
