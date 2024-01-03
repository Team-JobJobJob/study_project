package team01.studyCm.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import team01.studyCm.chat.dto.ChatRoomDto;
import team01.studyCm.chat.service.ChatService;
import team01.studyCm.chat.service.impl.ChatServiceImpl;

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

}
