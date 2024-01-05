package team01.studyCm.chat.service.impl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import team01.studyCm.chat.dto.ChatDto;
import team01.studyCm.chat.dto.ChatPageDto;
import team01.studyCm.chat.entity.Chat;
import team01.studyCm.chat.exception.UnauthorizedAccessException;
import team01.studyCm.chat.repository.ChatRepository;
import team01.studyCm.chat.service.ChatService;
import team01.studyCm.user.entity.User;
import team01.studyCm.user.repository.UserRepository;

import java.security.Principal;
import java.util.*;


@AllArgsConstructor
@Service
@Slf4j
public class ChatServiceImpl implements ChatService {

  private ChatRepository chatRepository;
  private UserRepository userRepository;

  @Autowired
  public ChatServiceImpl(ChatRepository chatRoomRepository) {
    this.chatRepository = chatRoomRepository;
  }

//  public void createRoom(ChatDto chatDto, User loggedInUser) {
//
//    loggedInUser = // 로그인한 사용자 정보를 가져오거나 생성
//            userRepository.save(loggedInUser);
//
//    Chat chat = Chat.toSaveEntity(chatDto, loggedInUser);
//
//    chatRepository.save(chat);
//
//  }

  public void createRoom(ChatDto chatDto,Principal principal) {
    String email = principal.getName();
    User user = userRepository.findByEmail(email).orElseThrow(
        () -> new RuntimeException("존재하지 않는 회원")
    );

    log.info("User email : {}", email);
    String job = user.getJob();

// getOtherDtoFields를 이용하여 다른 필드들을 가져옴


    // ChatRoomDto에서 필요한 정보를 이용하여 ChatRoom 엔티티 생성
    Chat chat = Chat.builder()
            .chatName(chatDto.getChatName())
            .description(chatDto.getDescription())
            .memberCnt(chatDto.getMemberCnt())
            .created_at(chatDto.getCreated_at())
            .modified_at(chatDto.getModified_at())
            .job(job)
            .user(user)
            .build();

    // ChatRoom 엔티티 저장
    chatRepository.save(chat);
  }

//  public void createRoom(ChatDto chatDto, User user) {
//
//    String email = user.getEmail();
//
//    // 사용자가 존재하지 않으면 예외를 던짐
//    user = userRepository.findByEmail(user.getEmail())
//            .orElseThrow(() -> new NoSuchElementException("User not found for email: " + email));
//
//    String job = user.getJob();
//
//// getOtherDtoFields를 이용하여 다른 필드들을 가져옴
//    Map<String, Object> otherDtoFields = chatDto.getOtherDtoFields();
//
//    // ChatRoomDto에서 필요한 정보를 이용하여 ChatRoom 엔티티 생성
//    Chat chat = Chat.builder()
//            .chatName(chatDto.getChatName())
//            .description(chatDto.getDescription())
//            .memberCnt(chatDto.getMemberCnt())
//            .created_at(chatDto.getCreated_at())
//            .modified_at(chatDto.getModified_at())
//            .email((String) otherDtoFields.get("email"))
//            .job((String) otherDtoFields.get("job"))
//            .user(user)
//            .build();
//
//    // ChatRoom 엔티티 저장
//    chatRepository.save(chat);
//  }


  @Override
  public void modifyRoom(ChatDto chatDto) {

    Optional<Chat> optionalChat = chatRepository.findById(chatDto.getChatId());

    if(optionalChat.isEmpty()){
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

  @Override
  public Page<ChatPageDto> getChatRoomList(Pageable pageable, int pageNo, String job,
      String orderCreteria) {
    pageable = PageRequest.of(pageNo, 10, Sort.by(Direction.ASC, orderCreteria));
    Page<Chat> chatPage = chatRepository.findByJob(job, pageable);

    Page<ChatPageDto> chatPageList = chatPage.map(
        chat -> new ChatPageDto(
            chat.getChatId(),
            chat.getChatName(),
            chat.getDescription(),
            chat.getMemberCnt(),
            chat.getJob()
        )
    );

    return chatPageList;
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
