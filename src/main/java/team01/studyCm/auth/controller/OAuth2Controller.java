package team01.studyCm.auth.controller;

import java.io.IOException;
import java.security.Principal;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import team01.studyCm.auth.service.OAuth2Service;

@RestController
@RequiredArgsConstructor
public class OAuth2Controller {

  private final OAuth2Service oAuth2UserService;
  private final ModelAndView modelAndView = new ModelAndView();


  @PostMapping("/oauth2/signup")
  public void updateUserInfo(HttpServletResponse response, @RequestParam String job, Principal principal) throws IOException {
    oAuth2UserService.updateOAuth2UserInfo(job,principal);

    response.sendRedirect("/chat/rooms/" + job);
  }

  @GetMapping("/oauth2/signup")
  public ModelAndView showUpdateUserInfo(Model model, Principal principal){
    model.addAttribute(principal);
    modelAndView.setViewName("OAuth2Update");
    return modelAndView;
  }


}