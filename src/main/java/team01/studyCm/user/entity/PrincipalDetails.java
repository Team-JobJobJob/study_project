package team01.studyCm.user.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import team01.studyCm.user.entity.status.Role;
import team01.studyCm.user.entity.status.SocialType;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class PrincipalDetails implements UserDetails, OAuth2User {
  private final String email;
  private final String password;
  private final String name;
  private final Role role;
  private final SocialType socialType;
  private Map<String, Object> attributes;



  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singleton(new SimpleGrantedAuthority("ROLE_"+role.name()));
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }


  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  @Override
  public String getName() {
    return email;
  }
  public static PrincipalDetails create(User user){
    return new PrincipalDetails(
        user.getEmail(),
        user.getPassword(),
        user.getUserName(),
        user.getRole(),
        user.getSocialType()
    );
  }

  public static PrincipalDetails create(User user, Map<String, Object> attributes) {
    PrincipalDetails details = create(user);
    details.setAttributes(attributes);

    return details;
  }

}