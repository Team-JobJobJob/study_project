package team01.studyCm.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team01.studyCm.config.TokenProvider;
import team01.studyCm.user.dto.LoginCredDto;
import team01.studyCm.user.dto.UserDto;
import team01.studyCm.user.dto.UserInfoDto;
import team01.studyCm.user.entity.User;
import team01.studyCm.user.entity.status.Role;
import team01.studyCm.user.repository.UserRepository;
import team01.studyCm.user.service.UserService;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean modify(Long userId, UserInfoDto userInfoDto) {
        String phone = userInfoDto.getPhone();
        String userName = userInfoDto.getUserName();
        String job = userInfoDto.getJob();
        String password = userInfoDto.getPassword();
        Optional<User> optionalUser = userRepository.findById(userId);

        if(optionalUser.isEmpty()){
            return false;
        }

        User user = optionalUser.get();
        user.setPhone(phone);
        user.setUserName(userName);
        user.setJob(job);
        user.setPassword(password);

        user.passwordEncode(passwordEncoder);

        userRepository.save(user);

        return true;
    }

    @Override
    public UserDto findById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent()) {
            return UserDto.toUserDto(optionalUser.get());
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteUser(LoginCredDto deleteDto) {
        String email = deleteDto.getEmail();
        String password = deleteDto.getPassword();
        Optional<User> optionalUser = userRepository.findByEmailAndPassword(email, password);

        if(optionalUser.isEmpty()){
            return false;
        }

        userRepository.delete(optionalUser.get());
        return true;
    }

    @Override
    public boolean signUp(UserDto userDto) {
        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());
        if(optionalUser.isPresent()){
            return false;
        }

        LocalDateTime currentTime = LocalDateTime.now();

        User newUser = User.builder()
                .userName(userDto.getUserName())
                .email(userDto.getEmail())
                .job(userDto.getJob())
                .created_at(currentTime)
                .modified_at(currentTime)
                .phone(userDto.getPhone())
                .password(userDto.getPassword())
                .role(Role.ROLE_USER)
                .build();

        newUser.passwordEncode(passwordEncoder);
        userRepository.save(newUser);
        return true;
    }

    @Override
    public Optional<UserDto> signIn(LoginCredDto signinDto) {
        String email = signinDto.getEmail();
        String password = signinDto.getPassword();
        Optional<User> optionalUser = userRepository.findByEmailAndPassword(email, password);

        if(optionalUser.isEmpty()){
            return Optional.empty();
        }

        return convertToUserDto(optionalUser.get());
    }

    public Optional<UserDto> convertToUserDto(User user) {
        return Optional.ofNullable(UserDto.builder()
                .user_id(user.getUser_id())
                .userName(user.getUsername())
                .email(user.getEmail())
                .job(user.getJob())
                .created_at(user.getCreated_at())
                .modified_at(user.getModified_at())
                .phone(user.getPhone())
                .password(user.getPassword())
                .role(user.getRole())
                .build());
    }
}
