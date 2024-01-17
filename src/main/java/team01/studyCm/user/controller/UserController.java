package team01.studyCm.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import team01.studyCm.auth.CustomOAuth2User;
import team01.studyCm.user.dto.LoginCredDto;
import team01.studyCm.user.dto.UserDto;
import team01.studyCm.user.dto.UserInfoDto;
import team01.studyCm.user.entity.PrincipalDetails;
import team01.studyCm.user.service.UserService;
import team01.studyCm.util.CookieUtility;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;


@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ModelAndView modelAndView = new ModelAndView();

    @GetMapping("/login")
    public String signIn() {
        return "index";
    }

    @PostMapping("/login")
    public ModelAndView signIn(@RequestBody LoginCredDto signinDto, Principal principal) {
        Optional<UserDto> userInfo = userService.signIn(signinDto);

        if(userInfo.isEmpty()) {
            modelAndView.setViewName("chatRooms/createRoom");
            return modelAndView;
        }

        System.out.println(principal);

        modelAndView.setViewName("chatRooms/createRoom");
        return modelAndView;

    }

    @GetMapping("users/signup")
    public String signUp() {
        return "users/signup";

    }

    @PostMapping("users/signup")
    public String signUpSubmit(Model model, HttpServletResponse response, @RequestBody UserDto userDto) {
        System.out.println("start");

        boolean userInfo = userService.signUp(userDto);

        if(!userInfo) {
            return "users/signup";
        }
        else{
            model.addAttribute("result", userInfo);
            CookieUtility.setUserCookie(response, userDto);
            log.info("회원가입 성공, 유저 아이디 : {}", userDto.getEmail());
        }

        return "index";
    }

    @GetMapping("users/withdraw")
    public String withdraw() {
        return "users/withdraw";
    }

    @PostMapping("users/withdraw")
    public String withdraw(@RequestBody LoginCredDto deleteDto) {

        boolean deleteOutcome = userService.deleteUser(deleteDto);

        if(!deleteOutcome) {
            return "users/withdraw";
        }

        //나중에 chat 목록으로 바꾸기
        return "users/mypage";
    }

    @GetMapping("users/modify")
    public String modify(HttpServletRequest request,  Model model, Authentication authentication) {
        String email;
        if(authentication != null) {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
            email = oAuth2User.getJob();
        }
        else{
            Map<String, String> map = CookieUtility.getCookie(request);
            email = map.get("userEmail");
        }

        UserDto userDto = userService.findByEmail(email);

        model.addAttribute("user", userDto);
        model.addAttribute("email", email);
        return "users/modify";
    }

    @PostMapping("users/modify")
    public String modifyPost(HttpServletRequest request, Authentication authentication, @RequestBody UserInfoDto InfoDto) {
        String email;

        if(authentication != null) {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
            email = oAuth2User.getEmail();
        }
        else{
            Map<String, String> map = CookieUtility.getCookie(request);
            email = map.get("userEmail");
        }

        boolean modifyOutcome = userService.modify(email, InfoDto);

        if(!modifyOutcome) {
            return "redirect:/chat/rooms/" + InfoDto.getJob();
        }

        return "redirect:/chat/rooms/" + InfoDto.getJob();
    }

    @GetMapping("users/mypage/{id}")
    public String findById(@PathVariable Long userId, Model model) {
        UserDto userDto = userService.findById(userId);
        model.addAttribute("user", userDto);
        return "users/myPage";
    }

}
