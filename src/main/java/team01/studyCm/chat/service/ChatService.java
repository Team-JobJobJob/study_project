package team01.studyCm.chat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import team01.studyCm.chat.dto.ChatRoomDto;
import team01.studyCm.chat.entity.ChatRoom;


public interface ChatService {

  boolean createRoom(ChatRoomDto chatRoomDto);

}
