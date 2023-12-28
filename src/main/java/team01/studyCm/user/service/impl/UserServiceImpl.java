package team01.studyCm.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team01.studyCm.user.dto.LoginCredDto;
import team01.studyCm.user.dto.UserDto;
import team01.studyCm.user.dto.UserInfoDto;
import team01.studyCm.user.entity.User;
import team01.studyCm.user.repository.UserRepository;
import team01.studyCm.user.service.UserService;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public boolean modify(Long userId, UserInfoDto userInfoDto) {
        String phone = userInfoDto.getPhone();
        String job = userInfoDto.getJob();
        String email = userInfoDto.getEmail();
        String password = userInfoDto.getPassword();
        Optional<User> optionalUser = userRepository.findById(userId);

        if(optionalUser.isEmpty()){
            return false;
        }

        User user = optionalUser.get();
        user.setPhone(phone);
        user.setJob(job);
        user.setEmail(email);
        user.setPassword(password);

        userRepository.save(user);

        return true;
    }



    @Override
    public boolean deleteUser(LoginCredDto deleteDto) {
        String id = deleteDto.getId();
        String password = deleteDto.getPassword();
        Optional<User> optionalUser = userRepository.findByIdAndPassword(id, password);

        if(optionalUser.isEmpty()){
            return false;
        }

        userRepository.delete(optionalUser.get());
        return true;
    }

    @Override
    public boolean signUp(UserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(userDto.getUser_id());
        if(optionalUser.isPresent()){
            return false;
        }

        LocalDateTime currentTime = LocalDateTime.now();

        User newUser = User.builder()
                .id(userDto.getId())
                .userName(userDto.getUserName())
                .email(userDto.getEmail())
                .job(userDto.getJob())
                .created_at(currentTime)
                .modified_at(currentTime)
                .phone(userDto.getPhone())
                .password(userDto.getPassword())
                .build();

        userRepository.save(newUser);

        return true;
    }

    @Override
    public Optional<UserDto> signIn(LoginCredDto signinDto) {
        String id = signinDto.getId();
        String password = signinDto.getPassword();
        Optional<User> optionalUser = userRepository.findByIdAndPassword(id, password);

        if(optionalUser.isEmpty()){
            return Optional.empty();
        }

        return convertToUserDto(optionalUser.get());
    }

    public Optional<UserDto> convertToUserDto(User user) {
        return Optional.ofNullable(UserDto.builder()
                .user_id(user.getUser_id())
                .id(user.getId())
                .userName(user.getUsername())
                .email(user.getEmail())
                .job(user.getJob())
                .created_at(user.getCreated_at())
                .modified_at(user.getModified_at())
                .phone(user.getPhone())
                .password(user.getPassword())
                .build());
    }
}
