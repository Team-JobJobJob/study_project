package team01.studyCm.user.service;

import static team01.studyCm.user.entity.status.SocialType.GOOGLE;
import static team01.studyCm.user.entity.status.SocialType.NAVER;

import java.security.Principal;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import team01.studyCm.auth.service.CustomOAuth2UserService;
import team01.studyCm.user.dto.LoginCredDto;
import team01.studyCm.user.dto.UserDto;
import team01.studyCm.user.dto.UserInfoDto;
import team01.studyCm.user.entity.User;
import team01.studyCm.user.entity.status.Role;
import team01.studyCm.user.entity.status.SocialType;
import team01.studyCm.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final CustomOAuth2UserService customOAuth2UserService;


  public boolean modify(Principal principal, UserInfoDto userInfoDto) {
    String phone = userInfoDto.getPhone();
    String userName = userInfoDto.getUserName();
    String job = userInfoDto.getJob();
    String password = userInfoDto.getPassword();

    User user = getUser(principal);
    user.setPhone(phone);
    user.setUserName(userName);
    user.setJob(job);
    user.setPassword(password);

    user.passwordEncode(passwordEncoder);

    userRepository.save(user);

    return true;
  }

  public User getUser(Principal principal){
    String email;

    OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken)principal;
    if (authenticationToken != null){
      email = getEmailByToken(authenticationToken);
    }else {
      email = principal.getName();
    }

    log.info("User email : {}", email);

    User user= userRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("존재하지 않는 회원"));


    return user;
  }

  private String getEmailByToken(OAuth2AuthenticationToken authenticationToken) {
    String email = null;

    SocialType socialType = customOAuth2UserService.getSocialType(
        authenticationToken.getAuthorizedClientRegistrationId());
    log.info("get SocialType : {}", socialType);

    if (GOOGLE == socialType) {
      email = authenticationToken.getPrincipal().getAttribute("email");  //구글
    } else if (socialType == NAVER) {
      Map<String, Object> attributes = authenticationToken.getPrincipal().getAttribute("response");
      if (attributes != null) {
        email = (String) attributes.get("email");
      }
    }else {
      Map<String, Object> attributes = authenticationToken.getPrincipal()
          .getAttribute("kakao_account");
      if (attributes != null) {
        email = (String) attributes.get("email");
      }
    }
    return email;
  }

  public UserDto findById(Long userId) {
    Optional<User> optionalUser = userRepository.findById(userId);
    if(optionalUser.isPresent()) {
      return UserDto.toUserDto(optionalUser.get());
    } else {
      return null;
    }
  }

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
        .role(Role.USER)
        .build();

    newUser.passwordEncode(passwordEncoder);
    userRepository.save(newUser);
    return true;
  }

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
