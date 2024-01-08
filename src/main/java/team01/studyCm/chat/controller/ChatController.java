package team01.studyCm.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import team01.studyCm.auth.CustomOAuth2User;
import team01.studyCm.chat.dto.ChatDto;
import team01.studyCm.chat.dto.ChatPageDto;
import team01.studyCm.chat.service.ChatService;
import team01.studyCm.user.entity.User;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RequestMapping("/chat")
@RequiredArgsConstructor
@RestController
public class ChatController {

  private final ChatService chatService;


  @GetMapping("")
  public String createRoom(Model model, Principal principal) {

    model.addAttribute(principal);
    return "chatRooms/createRoom";


  }

  @PostMapping("")
  public String createRoom(@ModelAttribute ChatDto chatDto, Principal principal) {

    //System.out.println("chatRoomDto = " + chatDto);

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

  @GetMapping("/modifyRoom/{chatId}")
  public String getModifyRoom(Model model, @PathVariable Long chatId) {
    Optional<ChatDto> chatData = chatService.chatValueById(chatId);
    model.addAttribute("chatId", chatId);
    ChatDto info = chatData.get();
    model.addAttribute("chatName", info.getChatName());
    model.addAttribute("description", info.getDescription());
    model.addAttribute("memberCnt", info.getMemberCnt());
    return "chatRooms/modifyRoom";
  }

  // 채팅방 수정
  @PostMapping("/modifyRoom/{chatId}")
  public String modifyRoom(@ModelAttribute @RequestBody ChatDto chatDto, Authentication authentication) {
    CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

    chatService.modifyRoom(chatDto);

    //추후에 수정
    return "redirect:/chat/rooms/" + oAuth2User.getJob();
  }

  // 채팅방 삭제
  @GetMapping("/deleteRoom/{chatId}")
  public String deleteRoom(@PathVariable Long chatId, Authentication authentication) {
    CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

    chatService.deleteRoom(chatId);

    //추후에 수정
    return "redirect:/chat/rooms/" + oAuth2User.getJob();
  }

  @GetMapping("/myChatList")
  public String myChatRooms(Model model, Authentication authentication) {
    CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
    List<ChatDto> chatRooms = chatService.allChatsByUserEmail(oAuth2User.getEmail());

    model.addAttribute("chatRooms", chatRooms);
    model.addAttribute("job", oAuth2User.getJob());

    //추후에 수정
    return "chatRooms/myChatList";
  }

  @GetMapping("/rooms/{job}")
  public String getChatList(Model model, @PathVariable String job,
      @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo,
      @RequestParam(required = false, defaultValue = "chatId", value = "orderby") String orderCreteria,
      Pageable pageable, @AuthenticationPrincipal User user, Authentication authentication) {

  Page<ChatPageDto> chatPageList = chatService.getChatRoomList(pageable, pageNo, job, orderCreteria);

    CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

    model.addAttribute("chatPageList", chatPageList);
    model.addAttribute("userId", oAuth2User.getUserId());

    return "chatRooms/ChatRoomList";
  }

}
