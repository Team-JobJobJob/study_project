package team01.studyCm.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

  // 채팅방 생성
  @PostMapping("/createRoom")
  public String createRoomSubmit(Model model, ChatRoomDto chatRoomDto) {

    boolean result = chatService.createRoom(chatRoomDto);

    if(!result) {
      return "CreateRoomFailed";
    }

    model.addAttribute("result", result);

    return "chatList";
  }

}
