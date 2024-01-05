package team01.studyCm.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import team01.studyCm.chat.dto.ChatDto;
import team01.studyCm.chat.dto.ChatPageDto;
import team01.studyCm.chat.service.ChatService;
import team01.studyCm.user.entity.User;

import java.security.Principal;
import java.util.List;

@RequestMapping("/chat")
@RequiredArgsConstructor
@Controller
public class ChatController {

  private final ChatService chatService;


  @GetMapping("")
  public String createRoom() {

    return "chatRooms/createRoom";


  }

  @PostMapping("")
  public String createRoom(@ModelAttribute ChatDto chatDto, Principal principal) {

    System.out.println("chatRoomDto = " + chatDto);

    chatService.createRoom(chatDto, principal);

    return "chatRooms/createRoomComplete";
  }

  // 채팅방 생성
//  @PostMapping("")
//  public String createRoom(@ModelAttribute ChatDto chatDto, User user, Principal principal) {
//
//    System.out.println("chatRoomDto = " + chatDto);
//
//    chatService.createRoom(chatDto, user, principal);
//
//    return "chatRooms/createRoomComplete";
//  }

//  @PostMapping("/{userId}")
//  public String createRoom(@ModelAttribute ChatDto chatDto, User user) {
//
//    System.out.println("chatRoomDto = " + chatDto);
//
//    chatService.createRoom(chatDto, user);
//
//    return "chatRooms/createRoomComplete";
//  }

  // 채팅방 수정
  @PostMapping("/modifyRoom/{chatId}")
  public String modifyRoom(@ModelAttribute ChatDto chatDto) {

    System.out.println("chatDto = " + chatDto);

    chatService.modifyRoom(chatDto);

    //추후에 수정
    return "chatRooms/myChatList";
  }

  // 채팅방 삭제
  @PostMapping("/deleteRoom/{chatId}")
  public String deleteRoom(@ModelAttribute ChatDto chatDto) {

    System.out.println("chatRoomDto = " + chatDto);

    chatService.deleteRoom(chatDto.getChatId());


    //추후에 수정
    return "chatRooms/myChatList";
  }

  @GetMapping("/myChatList/{userId}")
  public String myChatRooms(Model model, User user) {
    List<ChatDto> chatRooms = chatService.allChatsByUser(user);

    model.addAttribute("chatRooms", chatRooms);

    //추후에 수정
    return "chatRooms/myChatList";
  }

  @GetMapping("/rooms")
  public String getChatList(Model model, @RequestParam String job,
      @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo,
      @RequestParam(required = false, defaultValue = "chat_id", value = "orderby") String orderCreteria,
      Pageable pageable, @AuthenticationPrincipal User user) {

  Page<ChatPageDto> chatPageList = chatService.getChatRoomList(pageable, pageNo, job, orderCreteria);

    model.addAttribute("chatPageList", chatPageList);

    return "chatRooms/ChatRoomList";
  }

}
