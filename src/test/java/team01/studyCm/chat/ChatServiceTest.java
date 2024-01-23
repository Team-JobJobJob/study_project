package team01.studyCm.chat;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team01.studyCm.chat.dto.ChatDto;
import team01.studyCm.chat.entity.Chat;
import team01.studyCm.chat.repository.ChatRepository;
import team01.studyCm.chat.service.ChatService;
import team01.studyCm.user.entity.User;
import team01.studyCm.user.repository.UserRepository;

@SpringBootTest
class ChatServiceTest {

  @Autowired
  private ChatService chatService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ChatRepository chatRepository;

  String userEmail = "test1@gmail.com";
  String chatName = "코테 공부 같이해요";
  String newChatName = "백엔드 공부할 사람 모여라";


  @Test
  void createRoom() {

    User user = userRepository.findByEmail(userEmail)
        .orElseThrow(() -> new RuntimeException("존재하지 않는 회원"));

    ChatDto chatDto = new ChatDto();
    chatDto.setChatName(chatName);
    chatDto.setDescription("같이 매일 문제 풀어보고 풀이 방법 설명해요");
    chatDto.setMemberCnt(4);


    chatService.createRoom(chatDto, userEmail);

    Optional<Chat> chat = chatRepository.findFirstByChatName(chatName);
    assertThat(chat).isPresent();

    Chat createChat = chat.get();

    assertThat(createChat.getJob()).isEqualTo(user.getJob());
    assertThat(createChat.getChatName()).isEqualTo(chatName);
    System.out.println("채팅방 생성 성공");

  }

  @Test
  void modifyRoom() {

    ChatDto chatDto = new ChatDto();
    chatDto.setChatId(16L);
    chatDto.setChatName(newChatName);
    chatDto.setDescription("같이 매일 문제 풀어보고 풀이 방법 설명해요");
    chatDto.setMemberCnt(5);

    chatService.modifyRoom(chatDto);

    Optional<Chat> chat = chatRepository.findFirstByChatName(newChatName);
    assertThat(chat).isPresent();
    Chat updateChat = chat.get();

    assertThat(updateChat.getChatName()).isEqualTo(newChatName);
    assertThat(updateChat.getMemberCnt()).isEqualTo(5);
    System.out.println("채팅방 수정 성공");
  }

  @Test
  void deleteRoom() {
    Long chatId = 16L;
    chatService.deleteRoom(chatId);

    Optional<Chat> deleteChat = chatRepository.findById(chatId);

    assertThat(deleteChat).isEqualTo(Optional.empty());
    System.out.println("채팅방 삭제 성공");
  }
}