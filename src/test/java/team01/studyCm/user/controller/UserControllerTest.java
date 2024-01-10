package team01.studyCm.user.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import team01.studyCm.user.service.UserDetailService;
import team01.studyCm.user.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

  @Mock
  private UserService userService;

  @Mock
  private UserDetailService userDetailService;

  @Autowired
  private MockMvc mockMvc;

}