package team01.studyCm.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import team01.studyCm.user.dto.LoginCredDto;
import team01.studyCm.user.dto.UserDto;
import team01.studyCm.user.dto.UserInfoDto;
import team01.studyCm.user.repository.UserRepository;
import team01.studyCm.user.service.UserService;

import java.security.Principal;
import java.util.Optional;
import team01.studyCm.user.service.impl.UserServiceImpl;

@RestController
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
    public ModelAndView signUp() {
        modelAndView.setViewName("users/signup");
        return modelAndView;

    }

    @PostMapping("users/signup")
    public String signUpSubmit(Model model, @RequestBody UserDto userDto) {
        System.out.println("start");

        boolean userInfo = userService.signUp(userDto);

        if(!userInfo) {
            return "SignUpFailed";
        }

        model.addAttribute("result", userInfo);

        return "ChatList";
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

    @GetMapping("users/modify/{userId}")
    public String modify(Model model, @PathVariable Long userId) {

        model.addAttribute("userId", userId);
        return "users/modify";
    }

    @PostMapping("users/modify/{userId}")
    public String modify(@PathVariable Long userId, @RequestBody UserInfoDto InfoDto) {

        boolean modifyOutcome = userService.modify(userId, InfoDto);

        if(!modifyOutcome) {
            return "users/modify";
        }

        return "users/mypage";
    }

    @GetMapping("users/mypage/{id}")
    public String findById(@PathVariable Long userId, Model model) {
        UserDto userDto = userService.findById(userId);
        model.addAttribute("user", userDto);
        return "users/myPage";
    }

}
