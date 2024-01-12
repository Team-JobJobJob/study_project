package team01.studyCm.user.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import team01.studyCm.config.TokenDto;
import team01.studyCm.config.TokenProvider;
import team01.studyCm.user.dto.LoginCredDto;
import team01.studyCm.user.dto.UserDto;
import team01.studyCm.user.entity.PrincipalDetails;
import team01.studyCm.user.entity.User;
import team01.studyCm.user.repository.UserRepository;

import team01.studyCm.util.CookieUtility;

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


      SecurityContextHolder.getContext().setAuthentication(authentication);
      log.info("로그인에 성공하였습니다. 이메일 : {}", email);
      log.info("로그인에 성공하였습니다. AccessToken : {}", accessToken);


      PrincipalDetails userDetials = (PrincipalDetails) authentication.getPrincipal();
      String userEmail = userDetials.getEmail();
      log.info("get email from PrincipalDetails : {}", userEmail);
      User user = userRepository.findByEmail(userEmail)
          .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자"));

     

      UserDto userDto = UserDto.toUserDto(user);
      CookieUtility.setUserCookie(response, userDto);


//      TokenDto tokenDto = TokenDto.builder()
//          .accessToken(accessToken)
//          .refreshToken(refreshToken)
//          .userDto(UserDto.from(user))
//          .build();
//
//      // TokenDto를 JSON 문자열로 변환하여 응답
//      String tokenJson = new ObjectMapper().writeValueAsString(tokenDto);
//
//      response.setContentType("application/json");
//      response.setCharacterEncoding("UTF-8");
//      response.setStatus(HttpServletResponse.SC_OK);
//      response.getWriter().write(tokenJson);

      String job = user.getJob();
      response.sendRedirect("/chat/rooms/" + job);

    }
    catch (Exception e){
      throw e;
    }
  }

  private String extractUsername(Authentication authentication) {
    PrincipalDetails userDetails = (PrincipalDetails) authentication.getPrincipal();
    return userDetails.getUsername();
  }

}
