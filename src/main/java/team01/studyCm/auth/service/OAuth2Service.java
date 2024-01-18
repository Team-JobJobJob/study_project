package team01.studyCm.auth.service;

import static team01.studyCm.user.entity.status.SocialType.GOOGLE;
import static team01.studyCm.user.entity.status.SocialType.NAVER;

import java.security.Principal;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import team01.studyCm.exception.CustomErrorCode;
import team01.studyCm.exception.CustomException;
import team01.studyCm.user.entity.User;
import team01.studyCm.user.entity.status.Role;
import team01.studyCm.user.entity.status.SocialType;
import team01.studyCm.user.repository.UserRepository;


@RequiredArgsConstructor
@Service
@Slf4j
public class OAuth2Service {

  private final UserRepository userRepository;
  private final CustomOAuth2UserService customOAuth2UserService;

  public void updateOAuth2UserInfo(String job, Principal principal) {
    OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) principal;

    String email = getEmailByToken(authenticationToken);
    log.info("email: {}", email);

    User user = userRepository.findByEmail(email).
        orElseThrow(() -> {
          log.error("User not found for email: {}", email);
          return new CustomException(CustomErrorCode.NOT_FOUND_EMAIL);
        });

    log.debug("Updating user information: {}", user);
    user.setJob(job);
    user.setRole(Role.USER);
    userRepository.save(user);
  }

  private String getEmailByToken(OAuth2AuthenticationToken authenticationToken) {
    String email = null;

    SocialType socialType = customOAuth2UserService.getSocialType(
        authenticationToken.getAuthorizedClientRegistrationId());
    log.info("get SocialType : {}", socialType);

    if (GOOGLE == socialType) {
      email = authenticationToken.getPrincipal().getAttribute("email");  //구글
    } else if (socialType == NAVER) {
      Map<String, Object> attributes = authenticationToken.getPrincipal().getAttribute("response");
      if (attributes != null) {
        email = (String) attributes.get("email");
      }
    }else {
      Map<String, Object> attributes = authenticationToken.getPrincipal()
          .getAttribute("kakao_account");
      if (attributes != null) {
        email = (String) attributes.get("email");
      }
    }
    return email;

  }

}
