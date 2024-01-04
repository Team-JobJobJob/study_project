package team01.studyCm.chat.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team01.studyCm.chat.dto.ChatDto;
import team01.studyCm.chat.entity.Chat;
import team01.studyCm.chat.exception.UnauthorizedAccessException;
import team01.studyCm.chat.repository.ChatRepository;
import team01.studyCm.chat.service.ChatService;
import team01.studyCm.user.entity.User;
import team01.studyCm.user.repository.UserRepository;

import java.security.Principal;
import java.util.*;


@RequiredArgsConstructor
@Service
public class ChatServiceImpl implements ChatService {

  private ChatRepository chatRepository;
  private UserRepository userRepository;

  @Autowired
  public ChatServiceImpl(ChatRepository chatRoomRepository) {
    this.chatRepository = chatRoomRepository;
  }

//  public void createRoom(ChatDto chatDto) {
//
//    Chat chat = Chat.toSaveEntity(chatDto);
//
//    chatRepository.save(chat);
//
//  }

  public void createRoom(ChatDto chatDto, User user, Principal principal) {
    if (principal == null) {
      // Principal이 null이면 사용자가 인증되지 않은 상태임을 처리
      throw new UnauthorizedAccessException("User not authenticated");
    }

    String email = principal.getName();

    // 사용자가 존재하지 않으면 예외를 던짐
    user = userRepository.findByEmail(user.getEmail())
            .orElseThrow(() -> new NoSuchElementException("User not found for email: " + email));

    String job = user.getJob();

// getOtherDtoFields를 이용하여 다른 필드들을 가져옴
    Map<String, Object> otherDtoFields = chatDto.getOtherDtoFields();

    // ChatRoomDto에서 필요한 정보를 이용하여 ChatRoom 엔티티 생성
    Chat chat = Chat.builder()
            .chatName(chatDto.getChatName())
            .description(chatDto.getDescription())
            .memberCnt(chatDto.getMemberCnt())
            .created_at(chatDto.getCreated_at())
            .modified_at(chatDto.getModified_at())
            .email((String) otherDtoFields.get("email"))
            .job((String) otherDtoFields.get("job"))
            .user(user)
            .build();

    // ChatRoom 엔티티 저장
    chatRepository.save(chat);
  }


  @Override
  public void modifyRoom(ChatDto chatDto) {

    Optional<Chat> optionalChatRoom = chatRepository.findById(chatDto.getChatId());

    if(optionalChatRoom.isEmpty()){
      return ;
    }

    Chat chat = Chat.toSaveEntity(chatDto);

    chatRepository.save(chat);
  }

  @Override
  public void deleteRoom(Long roomId) {
    Optional<Chat> optionalChatRoom = chatRepository.findById(roomId);

    if(optionalChatRoom.isEmpty()){
      return ;
    }

    chatRepository.delete(optionalChatRoom.get());
  }

  @Override
  public List<ChatDto> allChatsByUser(User user) {
    List<Chat> chatRooms =  chatRepository.findAllByUser(user);
    List<ChatDto> ret = new ArrayList<>();
    for (Chat chat : chatRooms) {
      ret.add(convertToUserDto(chat));
    }
    return ret;
  }

  public ChatDto convertToUserDto(Chat chat) {
    return ChatDto.builder()
            .chatId(chat.getChatId())
            .created_at(chat.getCreated_at())
            .description(chat.getDescription())
            .modified_at(chat.getModified_at())
            .memberCnt(chat.getMemberCnt())
            .user(chat.getUser())
            .chatName(chat.getChatName())
            .build();
  }
}
