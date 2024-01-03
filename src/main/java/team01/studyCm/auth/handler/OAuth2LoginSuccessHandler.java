package team01.studyCm.auth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import team01.studyCm.auth.CustomOAuth2User;
import team01.studyCm.config.TokenProvider;
import team01.studyCm.user.entity.status.Role;
import team01.studyCm.user.repository.UserRepository;

@RequiredArgsConstructor
@Component
@Slf4j
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {
  private final UserRepository userRepository;
  private final TokenProvider tokenProvider;


  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    log.info("OAuth2 로그인 성공");
    try{
      CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
      String email = oAuth2User.getEmail();

      //처음 로그인하는 경우 GUEST로 role 설정, 추가 정보 입력받도록 redirect
      if(oAuth2User.getRole().equals(Role.GUEST)){
        String accessToken = tokenProvider.createAccessToken(oAuth2User.getEmail());
        response.addHeader("Authorization","Bearer "+accessToken);
        response.sendRedirect("/oauth2/signup");
      }else{
        loginSuccess(response, oAuth2User);
      }
    }catch (Exception e){
      throw e;
    }
  }

  private void loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User) {
    String accessToken = tokenProvider.createAccessToken(oAuth2User.getEmail());
    String refreshToken = tokenProvider.createRefreshToken();
    response.addHeader(tokenProvider.getAccessHeader(), "Bearer"+accessToken);
    response.addHeader(tokenProvider.getRefreshHeader(), "Bearer"+refreshToken);

    tokenProvider.sendAccessAndRefreshToken(response, accessToken, refreshToken);
    tokenProvider.updateRefreshToken(oAuth2User.getEmail(), refreshToken);

  }
}
