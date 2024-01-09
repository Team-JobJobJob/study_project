package team01.studyCm.user.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import team01.studyCm.auth.CustomOAuth2User;
import team01.studyCm.config.TokenProvider;
import team01.studyCm.user.dto.UserDto;
import team01.studyCm.user.entity.User;
import team01.studyCm.user.entity.status.Role;
import team01.studyCm.user.repository.UserRepository;

@RequiredArgsConstructor
@Component
@Slf4j
public class UserLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
  private final UserRepository userRepository;
  private final TokenProvider tokenProvider;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException {
    try {
      String email = extractUsername(authentication); // 인증 정보에서 Username(email) 추출
      String accessToken = tokenProvider.createAccessToken(email);
      String refreshToken = tokenProvider.createRefreshToken();

      tokenProvider.sendAccessAndRefreshToken(response, accessToken, refreshToken); // 응답 헤더에 AccessToken, RefreshToken 실어서 응답

      userRepository.findByEmail(email)
              .ifPresent(user -> {
                user.setRefreshToken(refreshToken);
                userRepository.saveAndFlush(user);
              });
      log.info("로그인에 성공하였습니다. 이메일 : {}", email);
      log.info("로그인에 성공하였습니다. AccessToken : {}", accessToken);


      // job을 불러올 수 있는 방법
      response.sendRedirect("/chat/rooms/IT");
    }
    catch (Exception e){
      throw e;
    }
  }

  private String extractUsername(Authentication authentication) {
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    return userDetails.getUsername();
  }

}
