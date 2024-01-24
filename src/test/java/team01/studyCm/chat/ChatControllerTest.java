package team01.studyCm.chat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import team01.studyCm.chat.dto.ChatDto;
import team01.studyCm.chat.service.ChatService;
import team01.studyCm.user.dto.LoginCredDto;
import team01.studyCm.user.dto.UserDto;
import team01.studyCm.user.dto.UserInfoDto;
import team01.studyCm.user.service.UserDetailService;
import team01.studyCm.user.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
class ChatControllerTest {

  @Mock
  private ChatService chatService;

  @Autowired
  private MockMvc mockMvc;

  private MockHttpServletRequest request;
  private ChatDto chatDto;


  @BeforeEach
  public void setup() {
    request = new MockHttpServletRequest();

    // Set cookies using addCookie method
    Cookie userEmail = new Cookie("user_email", "test1@gmail.com");
    Cookie userName = new Cookie("user_name", "test1");
    Cookie userJob = new Cookie("user_job", "WEBDEV");

    request.setCookies(userEmail, userName, userJob);

    chatDto = new ChatDto();
    chatDto.setChatName("안녕하세요");
    chatDto.setDescription("백엔드 공부 같이해요");
    chatDto.setMemberCnt(4);

  }

  @Test
  void createRoomTest() throws Exception {
    // Given
    doNothing().when(chatService).createRoom(any(ChatDto.class), anyString());

    // When + then
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/chat")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .flashAttr("chatDto", chatDto)
            .with(request -> {
              Cookie userEmail = new Cookie("user_email", "test1@gmail.com");
              Cookie userName = new Cookie("user_name", "test1");
              Cookie userJob = new Cookie("user_job", "WEBDEV");

              request.setCookies(userEmail, userName, userJob);
              return request;
            })
        )
        .andExpect(status().is3xxRedirection())
        .andExpect(MockMvcResultMatchers.redirectedUrl("/chat/rooms/" + chatDto.getJob()))
        .andReturn();


  }

  @Test
  void modifyRoom() throws Exception {
    // Given
    doNothing().when(chatService).modifyRoom(any(ChatDto.class));
    chatDto.setChatName("배고프다");
    chatDto.setDescription("안녕하세요");

    //modifyRoom 메소드 chatId pathVariable로 사용하는 걸로 보이는데(api) 메소드에서 변수는 그렇게 설정되어있지 않아 보입니다.
    //확인 후 수정 부탁드립니다(우진님)
    // When + then
    mockMvc.perform(MockMvcRequestBuilders.post("/chat/modifyRoom/{chatId}", 17L)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .flashAttr("chatDto", chatDto)
            .with(request -> {
              Cookie userEmail = new Cookie("user_email", "test1@gmail.com");
              Cookie userName = new Cookie("user_name", "test1");
              Cookie userJob = new Cookie("user_job", "WEBDEV");

              request.setCookies(userEmail, userName, userJob);
              return request;
            })
        )
        .andExpect(status().is3xxRedirection())
        .andExpect(MockMvcResultMatchers.redirectedUrl("/chat/rooms/IT" ))
        .andReturn();
  }

  @Test
  void deleteRoomTest() throws Exception {
    // Given
    doNothing().when(chatService).deleteRoom(24L);

    // When
    mockMvc.perform(MockMvcRequestBuilders.delete("/chat/deleteRoom/{chatId}", 24L)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .with(request -> {
              Cookie userEmail = new Cookie("user_email", "test1@gmail.com");
              Cookie userName = new Cookie("user_name", "test1");
              Cookie userJob = new Cookie("user_job", "WEBDEV");

              request.setCookies(userEmail, userName, userJob);
              return request;
            })
        )
        .andExpect(status().is3xxRedirection())
        .andExpect(MockMvcResultMatchers.redirectedUrl("/chat/rooms/WEBDEV"))
        .andReturn();

  }

}
