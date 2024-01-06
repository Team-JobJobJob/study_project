package team01.studyCm.user.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import team01.studyCm.user.dto.LoginCredDto;
import team01.studyCm.user.dto.UserDto;
import team01.studyCm.user.dto.UserInfoDto;
import team01.studyCm.user.service.UserService;

import java.security.Principal;
import java.util.Optional;

import team01.studyCm.util.CookieUtility;
import team01.studyCm.util.EnumUtility;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/signin")
    public String signIn() {
        return "index";
    }

    @PostMapping("/signin")
    public String signIn(Model model, HttpServletResponse response, LoginCredDto signinDto, Principal principal) {

        Optional<UserDto> userInfo = userService.signIn(signinDto);

        if(userInfo.isEmpty()) {
            return "chatRooms/createRoom";
        }

        CookieUtility.setUserCookie(response, userInfo.get());

        return "redirect:/chat/rooms/" + EnumUtility.jobEnumValueToName(userInfo.get().getJob());
    }

    @GetMapping("/signup")
    public String signUp() {
        return "users/signUp";
    }

    @PostMapping("/signup")
    public String signUpSubmit(Model model, UserDto userDto) {
        System.out.println("start");

        boolean userInfo = userService.signUp(userDto);

        if(!userInfo) {
            return "SignUpFailed";
        }

        model.addAttribute("result", userInfo);

        return "ChatList";
    }

    @GetMapping("/withdraw")
    public String withdraw() {
        return "users/withdraw";
    }

    @PostMapping("/withdraw")
    public String withdraw(LoginCredDto deleteDto) {

        boolean deleteOutcome = userService.deleteUser(deleteDto);

        if(!deleteOutcome) {
            return "users/withdraw";
        }

        //나중에 chat 목록으로 바꾸기
        return "users/mypage";
    }

    @GetMapping("/modify/{userId}")
    public String modify(Model model, @PathVariable Long userId) {

        model.addAttribute("userId", userId);
        return "users/modify";
    }

    @PostMapping("/modify/{userId}")
    public String modify(@PathVariable Long userId, UserInfoDto InfoDto) {

        boolean modifyOutcome = userService.modify(userId, InfoDto);

        if(!modifyOutcome) {
            return "users/modify";
        }

        return "users/mypage";
    }

    @GetMapping("/mypage/{id}")
    public String findById(@PathVariable Long userId, Model model) {
        UserDto userDto = userService.findById(userId);
        model.addAttribute("user", userDto);
        return "users/myPage";
    }

}
