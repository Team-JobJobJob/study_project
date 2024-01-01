package team01.studyCm.auth.service.impl;

import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team01.studyCm.auth.service.OAuth2Service;
import team01.studyCm.user.entity.User;
import team01.studyCm.user.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class OAuth2UserImpl implements OAuth2Service {

  private final UserRepository userRepository;
  @Override
  public void updateOAuth2UserInfo(String job, Principal principal) {
    String email = principal.getName();
    User user = userRepository.findByEmail(email).
        orElseThrow(() -> new RuntimeException("일치하지 않는 고객"));

    user.setJob(job);
    userRepository.save(user);
  }
}
