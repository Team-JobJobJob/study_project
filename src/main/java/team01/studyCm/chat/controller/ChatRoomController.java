package team01.studyCm.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import team01.studyCm.chat.dto.ChatRoomDto;
import team01.studyCm.chat.service.ChatService;
import team01.studyCm.chat.service.impl.ChatServiceImpl;
import team01.studyCm.user.entity.User;

import java.util.List;

@RequestMapping("/chat")
@RequiredArgsConstructor
@Controller
public class ChatRoomController {

  private final ChatService chatService;


  @GetMapping("/createRoom")
  public String createRoom() {
    return "chatRooms/createRoom";
  }

  @GetMapping
  public String chatPage(Authentication authentication, Model model) {
    if (authentication != null && authentication.isAuthenticated()) {
      String username = authentication.getName();
      // 여기에서 username을 사용하여 로그인한 사용자의 정보를 활용할 수 있음
      model.addAttribute("username", username);
      return "chatRooms/createRoom";
    } else {
      return "redirect:/login";
    }
  }
  // 채팅방 생성
  @PostMapping("/createRoom")
  public String createRoom(@ModelAttribute ChatRoomDto chatRoomDto) {

    System.out.println("chatRoomDto = " + chatRoomDto);

    chatService.createRoom(chatRoomDto);

    return "chatRooms/createRoomComplete";
  }

  // 채팅방 수정
  @PostMapping("/modifyRoom/{chatId}")
  public String modifyRoom(@ModelAttribute ChatRoomDto chatRoomDto) {

    System.out.println("chatRoomDto = " + chatRoomDto);

    chatService.modifyRoom(chatRoomDto);

    //추후에 수정
    return "chatRooms/myChatList";
  }

  // 채팅방 삭제
  @PostMapping("/deleteRoom/{chatId}")
  public String deleteRoom(@ModelAttribute ChatRoomDto chatRoomDto) {

    System.out.println("chatRoomDto = " + chatRoomDto);

    chatService.deleteRoom(chatRoomDto.getRoomId());


    //추후에 수정
    return "chatRooms/myChatList";
  }

  @GetMapping("/myChatList/{userId}")
  public String myChatRooms(Model model, User user) {
    List<ChatRoomDto> chatRooms = chatService.allChatsByUser(user);

    model.addAttribute("chatRooms", chatRooms);

    //추후에 수정
    return "chatRooms/myChatList";
  }

}
