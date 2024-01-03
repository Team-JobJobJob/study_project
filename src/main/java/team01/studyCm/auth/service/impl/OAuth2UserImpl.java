package team01.studyCm.auth.service.impl;

import java.security.Principal;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import team01.studyCm.auth.service.OAuth2Service;
import team01.studyCm.user.entity.User;
import team01.studyCm.user.entity.status.Role;
import team01.studyCm.user.repository.UserRepository;

@RequiredArgsConstructor
@Service
@Slf4j
public class OAuth2UserImpl implements OAuth2Service {

  private final UserRepository userRepository;
  @Override
  public void updateOAuth2UserInfo(String job,Principal principal) {
    OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) principal;

    String email = getEmailByToken(authenticationToken);
    if(email == null){
      log.error("Email not found: {}", email);
    }
    log.info("email: {}",email);

    log.debug("Received email for update: {}", email);
    User user = userRepository.findByEmail(email).
        orElseThrow(() -> {
            log.error("User not found for email: {}",email);
            return new RuntimeException("일치하지 않는 고객");});

    log.debug("Updating user information: {}", user);

    user.setJob(job);
    user.setRole(Role.ROLE_USER);
    userRepository.save(user);
  }

  private String getEmailByToken(OAuth2AuthenticationToken authenticationToken) {
    String email = authenticationToken.getPrincipal().getAttribute("email");

    if(email == null){
      Map<String, Object> attributes = authenticationToken.getPrincipal().getAttribute("response");
      if(attributes != null){
        email = (String) attributes.get("email");
      }
    }

    return email;

  }
}
