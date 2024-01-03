package team01.studyCm.auth.controller;

import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import team01.studyCm.auth.service.OAuth2Service;

@Controller
@RequiredArgsConstructor
public class OAuth2Controller {

  private final OAuth2Service oAuth2UserService;

  //추가 정보 입력
  @PostMapping("/oauth2/signup")
  public String updateUserInfo(@RequestParam String job,Principal principal){
    oAuth2UserService.updateOAuth2UserInfo(job,principal);

    return "index";
  }

  @GetMapping("/oauth2/signup")
  public String showUpdateUserInfo(Model model, Principal principal){
    model.addAttribute(principal);
    return "OAuth2Update";
  }


}
