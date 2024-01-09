package team01.studyCm.user.controller;

import jakarta.servlet.http.HttpServletResponse;
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

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ModelAndView modelAndView = new ModelAndView();

    @GetMapping("/login")
    public ModelAndView signIn() {
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @PostMapping("/login")
    public String signIn(@RequestBody LoginCredDto signinDto) {
        System.out.println("로그인 post");

        Optional<UserDto> userInfo = userService.signIn(signinDto);

        if(userInfo.isEmpty()) {
            modelAndView.setViewName("/login");
            return "redirect:/login";
        }

        modelAndView.setViewName("/chat/rooms/" + userInfo.get().getJob());

        return "redirect:/chat/rooms/" + userInfo.get().getJob();

    }

    @GetMapping("/users/signup")
    public ModelAndView signUp() {
        modelAndView.setViewName("users/signup");
        return modelAndView;

    }

    @PostMapping("/users/signup")
    public void signUpSubmit(HttpServletResponse response, @RequestBody UserDto userDto) throws IOException {
        System.out.println("start");

        boolean userInfo = userService.signUp(userDto);

        if(!userInfo) {
            response.sendRedirect("/users/signup");
        }
        else {
            System.out.println(userDto);
//            modelAndView.setViewName("/chat/rooms/" + userDto.getJob());
        }

        response.sendRedirect("/");
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
    public String modify(Model model, Principal principal) {
        String email = principal.getName();

        model.addAttribute("email", email);
        return "users/modify";
    }

    @PostMapping("users/modify")
    public String modify(Principal principal, @RequestBody UserInfoDto InfoDto) {

        boolean modifyOutcome = userService.modify(principal, InfoDto);

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

    @GetMapping("/username")
    @ResponseBody
    public String currentUserName(Principal principal) {
        return principal.getName();
    }

}
