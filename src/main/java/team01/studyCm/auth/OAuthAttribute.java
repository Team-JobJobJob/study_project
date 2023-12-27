package team01.studyCm.auth;

import java.util.Map;
import lombok.Getter;

@Getter
public class OAuthAttribute {
  private Map<String, Object> attributes;
  private String nameAttributeKey;

}
