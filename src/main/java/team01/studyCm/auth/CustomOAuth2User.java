package team01.studyCm.auth;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import team01.studyCm.user.entity.status.Role;

@Getter
public class CustomOAuth2User extends DefaultOAuth2User {

  private String email;

  private Role role;

  private String phoneNumber;
  /**
   * Constructs a {@code DefaultOAuth2User} using the provided parameters.
   *
   * @param authorities      the authorities granted to the user
   * @param attributes       the attributes about the user
   * @param nameAttributeKey the key used to access the user's &quot;name&quot; from
   *                         {@link #getAttributes()}
   */
  public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities,
      Map<String, Object> attributes, String nameAttributeKey,
      String email, Role role, String phoneNumber) {
    super(authorities, attributes, nameAttributeKey);

    //super -> 부모객체 default생성
    //email, role 추가 파라미터로 주입
    this.email = email;
    this.role = role;
    this.phoneNumber = phoneNumber;
  }

  private static Map<String, Object> getModifiedAttributes(Map<String, Object> attributes, String email, Role role, String phoneNumber) {
    Map<String, Object> modifiedAttributes = new HashMap<>(attributes);
    modifiedAttributes.put("email", email);

    return modifiedAttributes;
  }


}
