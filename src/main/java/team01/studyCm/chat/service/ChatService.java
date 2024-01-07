package team01.studyCm.chat.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import team01.studyCm.chat.dto.ChatDto;
import team01.studyCm.chat.dto.ChatPageDto;
import team01.studyCm.chat.entity.Chat;
import team01.studyCm.user.entity.User;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


public interface ChatService {

  void createRoom(ChatDto chatDto, Principal principal);
//  void createRoom(ChatDto chatDto, User user);

//  void createRoom(ChatDto chatDto,User loggedInUser);
  void modifyRoom(ChatDto chatDto);
  void deleteRoom(Long chatId);
  List<ChatDto> allChatsByUserEmail(String userId);

  Optional<ChatDto> chatValueById(Long chatId);


  Page<ChatPageDto> getChatRoomList(Pageable pageable, int pageNo, String job, String orderCreteria);
}
