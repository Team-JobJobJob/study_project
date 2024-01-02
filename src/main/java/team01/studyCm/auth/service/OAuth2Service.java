package team01.studyCm.auth.service;

import java.security.Principal;


public interface OAuth2Service {

  void updateOAuth2UserInfo(String job, Principal principal);

}
