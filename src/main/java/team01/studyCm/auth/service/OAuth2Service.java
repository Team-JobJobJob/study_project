package team01.studyCm.auth.service;

import java.security.Principal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;


public interface OAuth2Service {

  void updateOAuth2UserInfo(String job, @AuthenticationPrincipal Principal principal);

}
