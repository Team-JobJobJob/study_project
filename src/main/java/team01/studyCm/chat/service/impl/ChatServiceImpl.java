package team01.studyCm.chat.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team01.studyCm.chat.dto.ChatRoomDto;
import team01.studyCm.chat.entity.ChatRoom;
import team01.studyCm.chat.repository.ChatRoomRepository;
import team01.studyCm.chat.service.ChatService;



@RequiredArgsConstructor
@Service
public class ChatServiceImpl implements ChatService {

  private ChatRoomRepository chatRoomRepository;

  @Autowired
  public ChatServiceImpl(ChatRoomRepository chatRoomRepository) {
    this.chatRoomRepository = chatRoomRepository;
  }

  public void createRoom(ChatRoomDto chatRoomDto) {

    ChatRoom chatRoom = ChatRoom.toSaveEntity(chatRoomDto);

    chatRoomRepository.save(chatRoom);

  }
}
