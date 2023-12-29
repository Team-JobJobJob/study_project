package team01.studyCm.auth.userInfo;

import java.util.Map;

public abstract class OAuth2UserInfo {
  //자식 클래스에서만 사용 가능
  protected Map<String, Object> attributes;

  public OAuth2UserInfo(Map<String, Object> attributes){
    this.attributes = attributes;
  }

  //유저 정보들을 가져오는 메소드
  public abstract String getId(); //소셜 식별 값 : 구글 - "sub", 카카오 - "id", 네이버 - "id"
  public abstract String getName();

}
