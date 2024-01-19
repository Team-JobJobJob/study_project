package team01.studyCm.chat.controller;

import com.nimbusds.oauth2.sdk.http.HTTPRequest;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import team01.studyCm.auth.CustomOAuth2User;
import team01.studyCm.chat.dto.ChatDto;
import team01.studyCm.chat.dto.ChatPageDto;
import team01.studyCm.chat.service.ChatService;
import team01.studyCm.user.entity.PrincipalDetails;
import team01.studyCm.user.entity.User;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import team01.studyCm.user.service.UserService;
import team01.studyCm.util.CookieUtility;

@RequestMapping("/chat")
@RequiredArgsConstructor
@Controller
@Slf4j
public class ChatController {

  private final ChatService chatService;
  private final UserService userService;
  private final ModelAndView modelAndView = new ModelAndView();

  @GetMapping("")
  public String createRoom(HttpServletRequest request, Model model, Principal principal) {

    if(principal != null) {
      model.addAttribute(principal);
    }
    else{
      Map<String, String> map = CookieUtility.getCookie(request);
      String job = map.get("userJob");
      model.addAttribute("job", job);
    }

    return "chatRooms/createRoom";


  }

  @PostMapping("")
  public String createRoom(Model model, HttpServletRequest request, @ModelAttribute ChatDto chatDto, Principal principal) {

    if(principal != null) {
      chatService.createRoomWithPrincipal(chatDto, principal);
    }
    else {
      Map<String, String> map = CookieUtility.getCookie(request);
      String email = map.get("userEmail");
      chatService.createRoom(chatDto, email);
    }

    model.addAttribute("job", chatDto.getJob());

    return "redirect:/chat/rooms/" + chatDto.getJob();
  }


  @GetMapping("/modifyRoom/{chatId}")
  public String getModifyRoom(Model model, @PathVariable Long chatId) {
    Optional<ChatDto> chatData = chatService.chatValueById(chatId);
    model.addAttribute("chatId", chatId);
    ChatDto info = chatData.get();
    model.addAttribute("chatName", info.getChatName());
    model.addAttribute("description", info.getDescription());
    model.addAttribute("memberCnt", info.getMemberCnt());
    model.addAttribute("job", info.getJob());
    log.info(info.getJob());
    return "chatRooms/modifyRoom";
  }

  // 채팅방 수정
  @PostMapping("/modifyRoom/{chatId}")
  public String modifyRoom(HttpServletRequest request, @ModelAttribute @RequestBody ChatDto chatDto,
      Authentication authentication) {

    if(authentication != null) {
      CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
      chatService.modifyRoom(chatDto);

      return "redirect:/chat/rooms/" + oAuth2User.getJob();
    }
    else{
      Map<String, String> map = CookieUtility.getCookie(request);
      String job = map.get("userJob");
      chatService.modifyRoom(chatDto);

      return "redirect:/chat/rooms/" + job;
    }
  }

  // 채팅방 삭제
  //org.springframework.web.HttpRequestMethodNotSupportedException: Request method 'DELETE' is not supported
  @DeleteMapping("/deleteRoom/{chatId}")
  public String deleteRoom(HttpServletRequest request, @PathVariable Long chatId, Authentication authentication) {

    chatService.deleteRoom(chatId);

    log.info(String.valueOf(chatId));

    if(authentication == null) {
      Map<String, String> map = CookieUtility.getCookie(request);
      String job = map.get("userJob");
      log.info("return auth null" + job);
      return "redirect:/chat/rooms/" + job;
    }
    else {
      CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
      log.info("return " + oAuth2User.getJob());

      //추후에 수정
      return "redirect:/chat/rooms/" + oAuth2User.getJob();
    }
  }

  @GetMapping("/myChatList")
  public String myChatRooms(HttpServletRequest request, Model model, Principal principal) {
    List<ChatDto> chatRooms;
    String job;
    if(principal == null) {
      Map<String, String> map = CookieUtility.getCookie(request);
      chatRooms = chatService.allChatsByUserEmail(map.get("userEmail"));
      job = map.get("userJob");
    }
    else {
      chatRooms = chatService.allChatsByUserEmail(principal);
      User user = userService.getUser(principal);
      job = user.getJob();
    }

    model.addAttribute("chatRooms", chatRooms);
    model.addAttribute("job", job);

    log.info(String.valueOf(chatRooms));
    log.info(job);

    //추후에 수정
    return "chatRooms/myChatList";
  }

  @GetMapping("/rooms/{job}")
  public String getChatList(Model model, @PathVariable String job,
                                  @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo,
                                  @RequestParam(required = false, defaultValue = "chatId", value = "orderby") String orderCreteria,
                                  Pageable pageable) {

    log.info("rooms 입장 성공");
    Page<ChatPageDto> chatPageList = chatService.getChatRoomList(pageable, pageNo, job,
        orderCreteria);

    model.addAttribute("chatPageList", chatPageList);

//    modelAndView.setViewName("chatRooms/ChatRoomList");

    return "chatRooms/ChatRoomList";
  }

  @GetMapping("room/enter/{chatId}")
  public String roomDetail (Model model, @PathVariable Long chatId) {
    model.addAttribute("chatId", chatId);
    return "chatRooms/chatRoom";
  }

}
