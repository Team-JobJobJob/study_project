//package team01.studyCm.chat.controller;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.simp.SimpMessageSendingOperations;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import team01.studyCm.chat.dto.MessageDto;
//import team01.studyCm.chat.service.ChatService;
//
//import java.util.ArrayList;
//
//@Slf4j
//@RequiredArgsConstructor
//@Controller
//public class MessageController {
//
//    private final SimpMessageSendingOperations messagingTemplate;
//
//    @Autowired
//    ChatService chatService;
//    @MessageMapping("/chat/message")
//    public void message(MessageDto messageDto) {
//        if (MessageDto.MessageType.ENTER.equals(messageDto.getType()))
//            messageDto.setContents(messageDto.getSender() + " 님이 입장하셨습니다.");
//        messagingTemplate.convertAndSend("/sub/chat/room/" + messageDto.getChatId(), messageDto);
//
//    }
//
//    // 채팅에 참여한 유저 리스트 반환
//    @GetMapping("/chat/userlist")
//    @ResponseBody
//    public ArrayList<String> userList(Long chatId) {
//
//        return chatService.getUserList(chatId);
//    }
//
//}
