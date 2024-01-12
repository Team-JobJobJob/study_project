package team01.studyCm.user.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import team01.studyCm.config.TokenProvider;
import team01.studyCm.user.repository.UserRepository;

@Slf4j
public class UserLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, Exception exception
  ) throws IOException {
    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/plain;charset=UTF-8");
    response.getWriter().write("로그인 실패! 이메일이나 비밀번호를 확인해주세요.");
    log.info("로그인에 실패했습니다. 메시지 : {}", exception.getMessage());
  }
}