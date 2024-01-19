package team01.studyCm.chat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import team01.studyCm.auth.CustomOAuth2User;
import team01.studyCm.chat.dto.ChatDto;
import team01.studyCm.chat.dto.MessageCreateRequestDto;
import team01.studyCm.chat.dto.MessageDto;
import team01.studyCm.chat.entity.Chat;
import team01.studyCm.chat.repository.ChatRepository;
import team01.studyCm.chat.service.ChatService;
import team01.studyCm.config.TokenProvider;
import team01.studyCm.user.repository.UserRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MessageController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final TokenProvider tokenProvider;
    private final ChatService chatService;
    private final UserRepository userRepository;



//    @MessageMapping(value = "/chat/enter")
//    public void enter(MessageDto messageDto){
//        if (MessageDto.MessageType.ENTER.equals(messageDto.getType())) {
//            messageDto.setContents(messageDto.getSender() + "님이 채팅방에 참여하였습니다.");
//        }
//        messagingTemplate.convertAndSend("/sub/chat/room/" + messageDto.getChatId(), messageDto);
//
//    }


//    @MessageMapping("/chat/message/{chatId}")
//    @SendTo("/chat/room/{chatId}")
//    public void contents(@DestinationVariable Long chatId, MessageDto messageDto, CustomOAuth2User oAuth2User) {
//        String loginId = tokenProvider.createAccessToken(oAuth2User.getEmail());
//        if (MessageDto.MessageType.ENTER.equals(messageDto.getType())) {
//            messageDto.setContents(messageDto.getSender() + "님이 채팅방에 참여하였습니다.");
//        }
//        messagingTemplate.convertAndSend("/sub/chat/room/" + messageDto.getChatId(), messageDto);

        // DB에 채팅내용 저장
//        Chat chat= crr.findByChatId(messageDto.getChatId());
//        MessageCreateRequestDto chatMessageSaveDTO = new MessageCreateRequestDto(messageDto.getChatId(),messageDto.getSender(), messageDto.getContents());
//        cr.save(Message.toMessage(chatMessageSaveDTO,chat));


    // 채팅에 참여한 유저 리스트 반환
//    @GetMapping("/chat/userlist")
//    @ResponseBody
//    public ArrayList<String> userList(Long chatId) {
//
//        return chatService.getUserList(chatId);
//    }

    @MessageMapping("chat/enter")
    public void enterUser(@Payload MessageDto message, SimpMessageHeaderAccessor headerAccessor, Principal principal, String email) {
        chatService.incrementUserCount(message.getChatId());

        Long userId = chatService.addUserToRoom(message.getChatId(), message.getSender(), principal, email);

        headerAccessor.getSessionAttributes().put("userId", userId);
        headerAccessor.getSessionAttributes().put("chatId", message.getChatId());

        message.setContents(message.getSender() + " 님 입장!!");
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getChatId(), message); //sub
    }

    @MessageMapping("chat/sendMessage")
    public void sendMessage(@Payload MessageDto message) {
        log.info("CHAT {}", message);
        message.setContents(message.getContents());

        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getChatId(), message);
    }

    @EventListener
    public void handleWebSocketDisconnect(SessionDisconnectEvent event) {
        log.info("DisConnEvent {}", event);

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        Long userId = (Long) headerAccessor.getSessionAttributes().get("userId");
        Long chatId = (Long) headerAccessor.getSessionAttributes().get("chatId");

        log.info("headAccessor {}", headerAccessor);

        chatService.decrementUserCount(chatId);

        String username = chatService.getUserName(chatId, userId);
        chatService.removeUserFromRoom(chatId, userId);

        if (username != null) {
            log.info("User Disconnected : " + username);

            MessageDto message = MessageDto.builder()
                    .type(MessageDto.MessageType.LEAVE)
                    .sender(username)
                    .contents(username + " 님 퇴장!!")
                    .build();

            messagingTemplate.convertAndSend("/sub/chatroom/detail/" + chatId, message);
        }
    }

    @GetMapping("/chat/userlist")
    @ResponseBody
    public List<String> getUserList(Long chatId) {

        return chatService.getAllUsersInRoom(chatId);
    }

}