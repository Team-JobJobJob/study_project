package team01.studyCm.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import team01.studyCm.chat.dto.ChatRoomDto;
import team01.studyCm.chat.service.impl.ChatServiceImpl;

@RequestMapping("/chat")
@RequiredArgsConstructor
@Controller
public class ChatRoomController {

  private final ChatServiceImpl chatServiceimpl;


  @GetMapping("/createRoom")
  public String createRoom() {
    return "chat/createRoom";
  }

  // 채팅방 생성
  @ResponseBody
  @PostMapping("/room/creatRoom")
  public String createRoomSubmit(Model model, ChatRoomDto chatRoomDto) {

    boolean result = chatServiceimpl.createRoom(chatRoomDto);

    if(!result) {
      return "CreateRoomFailed";
    }

    model.addAttribute("result", result);

    return "chatList";
  }

}
