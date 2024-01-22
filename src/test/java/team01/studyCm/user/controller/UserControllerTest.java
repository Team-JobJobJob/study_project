package team01.studyCm.user.controller;

import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import team01.studyCm.user.dto.LoginCredDto;
import team01.studyCm.user.dto.UserDto;
import team01.studyCm.user.dto.UserInfoDto;
import team01.studyCm.user.service.UserDetailService;
import team01.studyCm.user.service.UserService;
import team01.studyCm.util.CookieUtility;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

  @Mock
  private UserService userService;

  @Mock
  private UserDetailService userDetailService;

  @Autowired
  private MockMvc mockMvc;

  private UserDto mockUserDto;
  private MockHttpServletRequest request;

  @BeforeEach
  public void setup() {
    request = new MockHttpServletRequest();

    // Set cookies using addCookie method
    Cookie userEmail = new Cookie("user_email", "test1@gmail.com");
    Cookie userName = new Cookie("user_name", "test1");
    Cookie userJob = new Cookie("user_job", "IT");

    request.setCookies(userEmail, userName, userJob);

    mockUserDto = new UserDto();
    mockUserDto.setUser_id(1L);
    mockUserDto.setEmail("test1@gmail.com");
    mockUserDto.setJob("IT");
    mockUserDto.setUserName("test1");
    mockUserDto.setPassword("123");

  }

  @Test
  void testSignInSuccess() throws Exception {
    // Given

    given(userService.signUp(any(UserDto.class)))
            .willReturn(true);

    // When/Then
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/users/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{ \"email\": \"test1@gmail.com\", \"userName\": \"test1\", \"password\": \"123\" }"))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("users/signup"))
            .andReturn();
  }

  @Test
  void testModifyPostSuccess() throws Exception {
    // Given
    UserInfoDto infoDto = new UserInfoDto();
    infoDto.setJob("IT");
    // Set necessary fields in infoDto
    given(userService.modify(any(String.class), any(UserInfoDto.class)))
            .willReturn(true);

    // When/Then
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/users/modify")
            .cookie(new Cookie("userEmail", "test1@gmail.com"))
            .contentType(MediaType.APPLICATION_JSON)
            .content("{ \"email\": \"test1@gmail.com\", \"job\": \"IT\", \"userName\": \"test1\", \"password\": \"123\", \"phone\": \"123\" }")) // Adjust the content accordingly
            .andExpect(status().isNotFound()) //redirected url
            .andReturn();
  }

  @Test
  void testDeleteSuccess() throws Exception {
    // Given
    // Set necessary fields in infoDto
    given(userService.deleteUser(any(LoginCredDto.class)))
            .willReturn(true);

    // When/Then
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/users/withdraw")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{ \"email\": \"test1@gmail.com\", \"password\": \"123\" }")) // Adjust the content accordingly
            .andExpect(MockMvcResultMatchers.view().name("users/withdraw"))
            .andReturn();
  }

}