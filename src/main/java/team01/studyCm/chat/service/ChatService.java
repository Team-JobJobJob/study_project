package team01.studyCm.chat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import team01.studyCm.chat.dto.ChatRoomDto;
import team01.studyCm.chat.entity.ChatRoom;
import team01.studyCm.user.entity.User;

import java.util.List;


public interface ChatService {

  void createRoom(ChatRoomDto chatRoomDto);
  void modifyRoom(ChatRoomDto chatRoomDto);
  void deleteRoom(Long roomId);
  List<ChatRoomDto> allChatsByUser(User user);
}
