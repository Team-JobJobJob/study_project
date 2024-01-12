package team01.studyCm.auth.userInfo;

import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo {
  public KakaoOAuth2UserInfo(Map<String, Object> attributes){
    super(attributes);
  }

  @Override
  public String getEmail() {
    Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");

    if (account == null) {
      return null;
    }

    return (String) account.get("email");
  }

  @Override
  public String getId() {
    return String.valueOf(attributes.get("id"));
  }

  @Override
  public String getName() {
    Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");

    if (account == null) {
      return null;
    }


    return (String) account.get("name");
  }

  @Override
  public String getPhone() {
    Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");

    if (account == null) {
      return null;
    }


    return (String) account.get("phone_number");
  }
}
