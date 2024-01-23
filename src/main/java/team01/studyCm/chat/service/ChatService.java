package team01.studyCm.chat.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import team01.studyCm.chat.dto.ChatDto;
import team01.studyCm.chat.dto.ChatPageDto;
import team01.studyCm.chat.entity.Chat;
import team01.studyCm.chat.entity.Message;
import team01.studyCm.chat.repository.ChatRepository;
import team01.studyCm.chat.repository.MessageRepository;
import team01.studyCm.user.entity.User;

import java.security.Principal;
import java.util.*;
import team01.studyCm.user.service.UserService;


@RequiredArgsConstructor
@Service
@Slf4j
public class ChatService {

  private final ChatRepository chatRepository;
  private final MessageRepository messageRepository;

  private final UserService userService;

  private Map<Long, Chat> chatRooms;

  @PostConstruct
  private void init() {
    chatRooms = new LinkedHashMap<>();
  }


  public void createRoomWithPrincipal(ChatDto chatDto,Principal principal) {
    User user = userService.getUser(principal);
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

    chatDto.setJob(job);

    this.chatRooms.put(chat.getChatId(), chat);

    // ChatRoom 엔티티 저장
    chatRepository.save(chat);
  }

  public void createRoom(ChatDto chatDto,String email) {
    User user = userService.getUserByEmail(email);
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

    this.chatRooms.put(chat.getChatId(), chat);

    chatDto.setJob(job);
    // ChatRoom 엔티티 저장
    chatRepository.save(chat);
  }


  public void modifyRoom(ChatDto chatDto) {

    Optional<Chat> optionalChat = chatRepository.findById(chatDto.getChatId());

    if(optionalChat.isEmpty()){
      return ;
    }

    Chat data = optionalChat.get();
    data.setChatName(chatDto.getChatName());
    data.setDescription(chatDto.getDescription());
    data.setMemberCnt(chatDto.getMemberCnt());
    chatDto.setJob(data.getJob());

    chatRepository.save(data);
  }

  public Optional<ChatDto> chatValueById(Long chatId) {
    Optional<Chat> optionalChat = chatRepository.findById(chatId);
    if(optionalChat.isEmpty()){
      return Optional.empty();
    }

    return Optional.of(convertToChatDto(optionalChat.get()));
  }

  public void deleteRoom(Long chatId) {
    Optional<Chat> optionalChatRoom = chatRepository.findById(chatId);

    if(optionalChatRoom.isEmpty()){
      return ;
    }

    chatRepository.delete(optionalChatRoom.get());
  }

  public List<ChatDto> allChatsByUserEmail(Principal principal) {
    User user = userService.getUser(principal);
    String email = user.getEmail();

    List<Chat> chatRooms =  chatRepository.findAllByUser_Email(email);
    List<ChatDto> ret = new ArrayList<>();
    for (Chat chat : chatRooms) {
      ret.add(convertToChatDto(chat));
    }
    return ret;
  }

  public List<ChatDto> allChatsByUserEmail(String email) {
    List<Chat> chatRooms =  chatRepository.findAllByUser_Email(email);
    List<ChatDto> ret = new ArrayList<>();
    for (Chat chat : chatRooms) {
      ret.add(convertToChatDto(chat));
    }
    return ret;
  }

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

  public ChatDto convertToChatDto(Chat chat) {
    return ChatDto.builder()
        .chatId(chat.getChatId())
        .created_at(chat.getCreated_at())
        .description(chat.getDescription())
        .modified_at(chat.getModified_at())
        .memberCnt(chat.getMemberCnt())
        .user(chat.getUser())
        .chatName(chat.getChatName())
            .job(chat.getJob())
        .build();
  }


  // 채팅 관련 메소드

  public List<String> getAllUsersInRoom(Long chatId) {
    return new ArrayList<>(getChatById(chatId).getUserList().values());
  }

  public void incrementUserCount(Long chatId) {
    updateUserCount(chatId, 1);
  }

  public void decrementUserCount(Long chatId) {
    updateUserCount(chatId, -1);
  }

  public void removeUserFromRoom(Long chatId, Long userId) {
    Chat chat = chatRepository.findByChatId(chatId);
    chat.getUserList().remove(userId);
  }

  private void updateUserCount(Long chatId, int count) {
    Chat chat = chatRepository.findByChatId(chatId);
    chat.setUserCnt(chat.getUserCnt() + count);
  }

  public Chat getChatById(Long chatId) {
    return chatRepository.findByChatId(chatId);
  }

  public Long addUserToRoom(Long chatId, String userName, Principal principal,String email) {
    Chat chat = chatRepository.findByChatId(chatId);

    if (principal == null) {
      User user = userService.getUserByEmail(email);
      Long userId = user.getUser_id();

      chat.getUserList().put(userId, userName);

      return userId;

    } else {
      User user = userService.getUser(principal);
      Long userId = user.getUser_id();

      chat.getUserList().put(userId, userName);

      return userId;

    }

  }

  public Message createChat(Long chatId, String sender, String contents) {
    Chat chat = chatRepository.findById(chatId).orElseThrow();  //방 찾기 -> 없는 방일 경우 여기서 예외처리
    return messageRepository.save(Message.createMessage(chat, sender, contents));
  }

//   채팅방 채팅내용 불러오기
//  public List<Message> findAllMessageByChatId(Long chatId) {
//    return messageRepository.findAllByChatId(chatId);
//  }

}
