package team01.studyCm.auth.controller;

import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import team01.studyCm.auth.service.OAuth2Service;

@Controller
@RequiredArgsConstructor
public class OAuth2Controller {

  private final OAuth2Service oAuth2UserService;

  //추가 정보 입력
  @PostMapping("/oauth2/signup")
  public String updateUserInfo(@RequestBody String job,Principal principal){
    oAuth2UserService.updateOAuth2UserInfo(job,principal);

    return "success";
  }


}
