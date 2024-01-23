package team01.studyCm.chat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import team01.studyCm.auth.CustomOAuth2User;
import team01.studyCm.chat.dto.ChatDto;
import team01.studyCm.chat.dto.MessageCreateRequestDto;
import team01.studyCm.chat.dto.MessageDto;
import team01.studyCm.chat.entity.Chat;
import team01.studyCm.chat.entity.Message;
import team01.studyCm.chat.repository.ChatRepository;
import team01.studyCm.chat.repository.MessageRepository;
import team01.studyCm.chat.service.ChatService;
import team01.studyCm.config.TokenProvider;
import team01.studyCm.user.entity.User;
import team01.studyCm.user.repository.UserRepository;

import javax.crypto.SecretKey;
import java.security.Principal;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MessageController {

    private final SimpMessagingTemplate template;
    private final ChatService chatService;
//    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;

    @MessageMapping("/chat/message/{chatId}") //여기로 전송되면 메서드 호출 -> WebSocketConfig prefixes 에서 적용한건 앞에 생략
    @SendTo("/sub/chat/room/{chatId}")   //구독하고 있는 장소로 메시지 전송 (목적지)  -> WebSocketConfig Broker 에서 적용한건 앞에 붙어줘야됨
    public MessageDto message(@DestinationVariable Long chatId, MessageDto messageDto) {

        //채팅 저장
        Message message = chatService.createChat(chatId, messageDto.getSender(), messageDto.getContents());
        return MessageDto.builder()
                .chatId(chatId)
                .sender(message.getSender())
                .contents(message.getContents())
                .build();
    }

//    @MessageMapping(value = "/chat/enter/{chatId}")
//    public void enter(MessageDto messageDto){
//        messageDto.setContents(messageDto.getSender() + "님이 채팅방에 참여하였습니다.");
//        template.convertAndSend("/sub/chat/room/" + messageDto.getChatId(), messageDto);
//    }

//    @MessageMapping(value = "/chat/message/{chatId}")
//    public void message(@DestinationVariable Long chatId, MessageDto messageDto){
//
//        template.convertAndSend("/sub/chat/room/" + messageDto.getChatId(), messageDto);
//    }


}