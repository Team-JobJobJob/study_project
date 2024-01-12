package team01.studyCm.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import team01.studyCm.user.entity.User;
import team01.studyCm.user.repository.UserRepository;


@RequiredArgsConstructor
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  public static final String TOKEN_HEADER = "Authorization";
  public static final String TOKEN_PREFIX = "Barer";

  private static final String NO_CHECK_URL = "/login";

  private final TokenProvider tokenProvider;
  private final UserRepository userRepository;
  private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain)
      throws ServletException, IOException {
    if (request.getRequestURI().equals(NO_CHECK_URL)) {
      filterChain.doFilter(request, response);
      return;
    }

    String refreshToken = tokenProvider.extractRefreshToken(request)
        .filter(tokenProvider::isTokenValid)
        .orElse(null);

    if (refreshToken != null) {
      checkRefreshTokenAndReissueAccessToken(response, refreshToken);
      return;
    }

    if (refreshToken == null) {
      checkAccessTokenAndAuthentication(request, response, filterChain);
    }
  }


  private void checkAccessTokenAndAuthentication(HttpServletRequest request,
      HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException{

    tokenProvider.extractAccessToken(request)
        .filter(tokenProvider::isTokenValid)
        .ifPresent(accessToken -> {
          log.info("AccessToken successfully extracted: {}", accessToken);
          tokenProvider.extractEmail(accessToken)
              .ifPresent(email -> userRepository.findByEmail(email)
                  .ifPresent(user -> {
                    saveAuthentication(user);
                  })
              );
        });

    filterChain.doFilter(request, response);
  }

  //refreshToken으로 유저 정보 찾기 + token재발급
  public void checkRefreshTokenAndReissueAccessToken(HttpServletResponse response,
      String refreshToken) {
    userRepository.findByRefreshToken(refreshToken)
        .ifPresent(user -> {
          String reissueRefreshToken = reissueUpdateRefreshToken(user);
          tokenProvider.sendAccessAndRefreshToken(response,
              tokenProvider.createAccessToken(user.getEmail()), reissueRefreshToken);
        });

  }

  //refresh token재발급 + db 업데이트
  private String reissueUpdateRefreshToken(User user) {
    String reissueRefreshToken = tokenProvider.createRefreshToken();
    user.setRefreshToken(reissueRefreshToken);
    userRepository.saveAndFlush(user);
    return reissueRefreshToken;
  }


  public void saveAuthentication(User user) {
    String password = user.getPassword();
    if (password == null) {
      password = generateRandomPassword(); //소셜로그인 유저 비밀번호 임의 설정
    }

    UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
        .username(user.getEmail())
        .password(password)
        .roles(user.getRole().name())
        .build();

    Authentication authentication = new UsernamePasswordAuthenticationToken(
        userDetails, null, authoritiesMapper.mapAuthorities(userDetails.getAuthorities())
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  private String generateRandomPassword() {
    int index = 0;
    char[] charSet = new char[]{
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
        'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };  //배열안의 문자 숫자는 원하는대로

    StringBuffer password = new StringBuffer();
    Random random = new Random();

    for (int i = 0; i < 8; i++) {
      double rd = random.nextDouble();
      index = (int) (charSet.length * rd);

      password.append(charSet[index]);
    }
    System.out.println(password);
    return password.toString();
  }


}