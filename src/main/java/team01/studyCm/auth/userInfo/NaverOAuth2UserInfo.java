package team01.studyCm.auth.userInfo;

import java.util.Map;
import java.util.Objects;

public class NaverOAuth2UserInfo extends OAuth2UserInfo{

  public NaverOAuth2UserInfo(Map<String, Object> attributes){
    super(attributes);
  }

  @Override
  public String getEmail() {
    Map<String, Object> response = (Map<String, Object>) attributes.get("response");

    if (response == null) {
      return null;
    }

    return (String) response.get("email");
  }

  //response key로 한 번 감싸져 있어 get("response")로 꺼낸 뒤 사용할 정보 key로 사용
  @Override
  public String getId() {
    Map<String, Object> response = (Map<String, Object>) attributes.get("response");

    if (response == null) {
      return null;
    }
    return (String) response.get("id");
  }

  @Override
  public String getName() {
    Map<String, Object> response = (Map<String, Object>) attributes.get("response");

    if (response == null) {
      return null;
    }

    return (String) response.get("name");
  }

  @Override
  public String getPhone() {
    Map<String, Object> response = (Map<String, Object>) attributes.get("response");

    if (response == null) {
      return null;
    }

    return (String) response.get("phone");
  }
}
