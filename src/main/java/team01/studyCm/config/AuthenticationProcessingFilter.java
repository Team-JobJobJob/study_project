package team01.studyCm.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;

public class AuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {


  private static final String LOGIN_URL = "/login";
  private static final String HTTP_METHOD = "POST";
  private static final String CONTENT_TYPE = "application/json";
  private static final String USERNAME = "email";
  private static final String PASSWORD = "password";
  private static final AntPathRequestMatcher DEFAULT_LOGIN_PATH_REQUEST_MATCHER =
      new AntPathRequestMatcher(LOGIN_URL, HTTP_METHOD); // "/login" + POST로 온 요청에 매칭된다.

  private final ObjectMapper objectMapper;

  public AuthenticationProcessingFilter(ObjectMapper objectMapper) {
    super(DEFAULT_LOGIN_PATH_REQUEST_MATCHER); // 위에서 설정한 "login" + POST로 온 요청을 처리하기 위해 설정
    this.objectMapper = objectMapper;
  }


  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
    if(request.getContentType() == null || !request.getContentType().equals(CONTENT_TYPE)  ) {
      throw new RuntimeException("Authentication Content-Type not supported: " + request.getContentType());
    }

    String messageBody = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);

    Map<String, String> usernamePasswordMap = objectMapper.readValue(messageBody, Map.class);

    String email = usernamePasswordMap.get(USERNAME);
    String password = usernamePasswordMap.get(PASSWORD);

    UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(email, password);//principal 과 credentials 전달

    return this.getAuthenticationManager().authenticate(authRequest);
  }
}
