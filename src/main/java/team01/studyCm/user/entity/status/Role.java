package team01.studyCm.user.entity.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
  //자체 회원가입 시 role = user
  //OAuth2 로그인 시 첫 로그인에는 Guest -> 추가 정보 입력 시 user로 업데이트
  ROLE_GUEST("게스트"),
  ROLE_USER("유저");

  private final String key;


}
