package team01.studyCm.user.service;

import team01.studyCm.user.dto.LoginCredDto;
import team01.studyCm.user.dto.UserDto;
import team01.studyCm.user.dto.UserInfoDto;

import java.util.Optional;

public interface UserService {
    Optional<UserDto> signIn(LoginCredDto signinDto);
    boolean signUp(UserDto userDto);
    boolean deleteUser(LoginCredDto deleteDto);
    boolean modify(Long userId, UserInfoDto userInfoDto);
}