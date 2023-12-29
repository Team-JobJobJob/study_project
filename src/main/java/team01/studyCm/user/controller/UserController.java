package team01.studyCm.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team01.studyCm.user.dto.LoginCredDto;
import team01.studyCm.user.dto.UserDto;
import team01.studyCm.user.dto.UserInfoDto;
import team01.studyCm.user.repository.UserRepository;
import team01.studyCm.user.service.UserService;

import java.util.Optional;
import team01.studyCm.user.service.impl.UserServiceImpl;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private final UserService userService;

    private final UserServiceImpl userServiceImpl;

    @GetMapping("/signin")
    public String signIn(@RequestBody LoginCredDto signinDto) {

        Optional<UserDto> userInfo = userService.signIn(signinDto);

        if(userInfo.isEmpty()) {
            return "SignInFailed";
        }

        return "ChatList";
    }

    @PostMapping("/signup")
    public String signup(@RequestBody UserDto userDto) {

        boolean userInfo = userService.signUp(userDto);

        if(!userInfo) {
            return "SignUpFailed";
        }

        return "ChatList";
    }

    @DeleteMapping("/withdraw")
    public String withdraw(@RequestBody LoginCredDto deleteDto) {

        boolean deleteOutcome = userService.deleteUser(deleteDto);

        if(!deleteOutcome) {
            return "DeleteFailed";
        }

        return "ChatList";
    }

    @PutMapping("/modify/{userId}")
    public String modify(@PathVariable Long userId, @RequestBody UserInfoDto InfoDto) {

        boolean modifyOutcome = userService.modify(userId, InfoDto);

        if(!modifyOutcome) {
            return "ModifyFailed";
        }

        return "ChatList";
    }

}
