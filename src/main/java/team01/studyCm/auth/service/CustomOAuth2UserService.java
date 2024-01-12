package team01.studyCm.auth.service;//package team01.studyCm.auth.service;

import java.util.Collections;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import team01.studyCm.auth.CustomOAuth2User;
import team01.studyCm.auth.OAuthAttributes;
import team01.studyCm.user.entity.status.SocialType;
import team01.studyCm.user.repository.UserRepository;
import team01.studyCm.user.entity.User;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
  private final UserRepository userRepository;

  private static final String NAVER = "naver";
  private static final String KAKAO = "kakao";


  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

    OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
    OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);


    String registrationId = userRequest.getClientRegistration().getRegistrationId();
    SocialType socialType = getSocialType(registrationId);
    String userNameAttributeName = userRequest.getClientRegistration()

        .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
    Map<String, Object> attributes = oAuth2User.getAttributes();


    OAuthAttributes extractAttributes = OAuthAttributes.of(socialType, userNameAttributeName, attributes);
    User createdUser = getUser(extractAttributes, socialType);

    return new CustomOAuth2User(
        Collections.singleton(new SimpleGrantedAuthority(createdUser.getRole().name())),
        attributes,
        extractAttributes.getNameAttributeKey(),
        createdUser.getEmail(),
        createdUser.getUser_id(),
        createdUser.getJob(),
        createdUser.getRole(),
        createdUser.getPhone()
    );
  }

  public static SocialType getSocialType(String registrationId) {
    if(NAVER.equals(registrationId)) {
      return SocialType.NAVER;
    }
    if(KAKAO.equals(registrationId)) {
      return SocialType.KAKAO;
    }
    return SocialType.GOOGLE;
  }

  private User getUser(OAuthAttributes attributes, SocialType socialType) {
    User findUser = userRepository.findBySocialTypeAndSocialId(socialType,
        attributes.getOAuth2UserInfo().getId()).orElse(null);

    if(findUser == null) {
      return saveUser(attributes, socialType);
    }
    return findUser;
  }

  private User saveUser(OAuthAttributes attributes, SocialType socialType) {
    User createdUser = attributes.toEntity(socialType, attributes.getOAuth2UserInfo());
    return userRepository.save(createdUser);
  }


}

