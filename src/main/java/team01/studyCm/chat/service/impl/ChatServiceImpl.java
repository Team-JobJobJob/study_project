package team01.studyCm.chat.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team01.studyCm.chat.dto.ChatRoomDto;
import team01.studyCm.chat.entity.ChatRoom;
import team01.studyCm.chat.repository.ChatRoomRepository;
import team01.studyCm.chat.service.ChatService;
import team01.studyCm.user.dto.UserDto;
import team01.studyCm.user.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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

  @Override
  public void modifyRoom(ChatRoomDto chatRoomDto) {

    Optional<ChatRoom> optionalChatRoom = chatRoomRepository.findById(chatRoomDto.getRoomId());

    if(optionalChatRoom.isEmpty()){
      return ;
    }

    ChatRoom chatRoom = ChatRoom.toSaveEntity(chatRoomDto);

    chatRoomRepository.save(chatRoom);
  }

  @Override
  public void deleteRoom(Long roomId) {
    Optional<ChatRoom> optionalChatRoom = chatRoomRepository.findById(roomId);

    if(optionalChatRoom.isEmpty()){
      return ;
    }

    chatRoomRepository.delete(optionalChatRoom.get());
  }

  @Override
  public List<ChatRoomDto> allChatsByUser(User user) {
    List<ChatRoom> chatRooms =  chatRoomRepository.findAllByUser(user);
    List<ChatRoomDto> ret = new ArrayList<>();
    for (ChatRoom chatRoom : chatRooms) {
      ret.add(convertToUserDto(chatRoom));
    }
    return ret;
  }

  public ChatRoomDto convertToUserDto(ChatRoom chatRoom) {
    return ChatRoomDto.builder()
            .roomId(chatRoom.getRoomId())
            .created_at(chatRoom.getCreated_at())
            .description(chatRoom.getDescription())
            .modified_at(chatRoom.getModified_at())
            .number(chatRoom.getNumber())
            .user(chatRoom.getUser())
            .roomName(chatRoom.getRoomName())
            .build();
  }
}
