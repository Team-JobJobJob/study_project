package team01.studyCm.chat.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import team01.studyCm.chat.dto.ChatDto;
import team01.studyCm.chat.dto.ChatPageDto;
import team01.studyCm.chat.entity.Chat;
import team01.studyCm.user.entity.User;

import java.security.Principal;
import java.util.List;


public interface ChatService {

  void createRoom(ChatDto chatDto, Principal principal);
//  void createRoom(ChatDto chatDto, User user);

//  void createRoom(ChatDto chatDto,User loggedInUser);
  void modifyRoom(ChatDto chatDto);
  void deleteRoom(Long chatId);
  List<ChatDto> allChatsByUser(User user);


  Page<ChatPageDto> getChatRoomList(Pageable pageable, int pageNo, String job, String orderCreteria);
}
