package team01.studyCm.chat.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team01.studyCm.chat.dto.ChatRoomDto;
import team01.studyCm.chat.entity.ChatRoom;
import team01.studyCm.chat.repository.ChatRoomRepository;
import team01.studyCm.chat.service.ChatService;



@RequiredArgsConstructor
@Service
public class ChatServiceImpl implements ChatService {

  private ChatRoomRepository chatRoomRepository;


  @Override
  public boolean createRoom(ChatRoomDto chatRoomDto) {


    ChatRoom chatRoom = ChatRoom.builder()
        .roomName(chatRoomDto.getRoomName())
        .number(chatRoomDto.getNumber())
        .description(chatRoomDto.getDescription())
        .created_at(LocalDateTime.now())
        .modified_at(LocalDateTime.now())
        .build();

    chatRoomRepository.save(chatRoom);

    return true;
  }
}
