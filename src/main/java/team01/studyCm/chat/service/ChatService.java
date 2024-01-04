package team01.studyCm.chat.service;


import team01.studyCm.chat.dto.ChatDto;
import team01.studyCm.user.entity.User;

import java.security.Principal;
import java.util.List;


public interface ChatService {

  void createRoom(ChatDto chatDto, User user, Principal principal);

  void modifyRoom(ChatDto chatDto);
  void deleteRoom(Long chatId);
  List<ChatDto> allChatsByUser(User user);
}
