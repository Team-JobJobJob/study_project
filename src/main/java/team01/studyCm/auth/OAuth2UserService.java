package team01.studyCm.auth;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class OAuth2UserService extends DefaultOAuth2UserService {
  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{

    //loadUser메소드 실행될 시점에 Access Token 정상 발급, AccessToken으로 User정보 조회
    //유저 정보 조회
    OAuth2User oAuth2User = super.loadUser(userRequest);

    //권한 부여
    List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");

    String userNameAttributeName = userRequest.getClientRegistration()
        .getProviderDetails()
        .getUserInfoEndpoint()
        .getUserNameAttributeName();

    return new DefaultOAuth2User(authorities, oAuth2User.getAttributes(),userNameAttributeName);
  }

}
