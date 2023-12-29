package team01.studyCm.config;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import team01.studyCm.user.repository.UserRepository;
import team01.studyCm.user.service.UserDetailService;

@Component
@RequiredArgsConstructor
@Getter
@Slf4j
public class TokenProvider {

  private static final String KEY_ROLES = "role";
  private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60; //1h
  private final UserDetailService userDetailService;
  private final UserRepository userRepository;

  @Value("${jwt.secretKey}")
  private String secretKey;

  @Value("${jwt.access.header}")
  private String accessHeader;

  @Value("${jwt.refresh.header}")
  private String refreshHeader;

  public String createAccessToken(String email) {
    Date now = new Date();
    Date expiredDate = new Date(now.getTime() + TOKEN_EXPIRE_TIME);

    return Jwts.builder()
        .setSubject("AccessToken")
        .setIssuedAt(now)
        .setExpiration(expiredDate)
        .signWith(SignatureAlgorithm.HS512, this.secretKey)
        .claim("email",email)
        .compact();

  }

  public String getUsername(String token) {
    return this.parseClaims(token).getSubject();
  }

  public boolean validateToken(String token) {
    if (!StringUtils.hasText(token)) {
      return false;
    }

    var claims = this.parseClaims(token);
    return !claims.getExpiration().before(new Date());
  }

  private Claims parseClaims(String token) {
    try {
      return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
    } catch (ExpiredJwtException e) {
      return e.getClaims();
    }
  }

  public Authentication getAuthentication(String jwt) {
    UserDetails userDetails = this.userDetailService.loadUserByUsername(this.getUsername(jwt));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  private void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
    response.setHeader(accessHeader, accessToken);
  }

  //refreshToken
  public String createRefreshToken() {
    Date now = new Date();
    Date expiredDate = new Date(now.getTime() + TOKEN_EXPIRE_TIME);

    return Jwts.builder()
        .setSubject("RefreshToken")
        .setIssuedAt(now)
        .setExpiration(expiredDate)
        .signWith(SignatureAlgorithm.HS512, this.secretKey)
        .compact();
  }

  public void updateRefreshToken(String email, String refreshToken) {
    userRepository.findByEmail(email)
        .ifPresentOrElse(user -> user.updateRefreshToken(refreshToken),
            () -> new Exception("일치하는 회원이 없습니다"));
  }

  private void setRefreshTokenHeader(HttpServletResponse response, String refreshToken) {
    response.setHeader(refreshHeader, refreshToken);

  }

  public void sendAccessToken(HttpServletResponse response, String accessToken) {
    response.setStatus(HttpServletResponse.SC_OK);
    response.setHeader(accessHeader, accessToken);
    response.setHeader(accessHeader, accessToken);
  }

  public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken,
      String refreshToken) {
    response.setStatus(HttpServletResponse.SC_OK);
    setAccessTokenHeader(response, accessToken);
    setRefreshTokenHeader(response, refreshToken);

  }

  // 헤더에서 RefreshToken추출 ->  헤더 가져온 후 Bearer삭제
  public Optional<String> extractRefreshToken(HttpServletRequest request) {
    return Optional.ofNullable(request.getHeader(refreshHeader))
        .filter(refreshToken -> refreshToken.startsWith("Bearer"))
        .map(refreshToken -> refreshToken.replace("Bearer", ""));
  }

  //헤더에서 AccessToken추출
  public Optional<String> extractAccessToken(HttpServletRequest request) {
    return Optional.ofNullable(request.getHeader(accessHeader))
        .filter(accessToken -> accessToken.startsWith("Bearer"))
        .map(accessToken -> accessToken.replace("Bearer",""));
  }

  public boolean isTokenValid(String token) {
    try {
      JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token);
      return true;
    } catch (Exception e) {
      log.error("유효하지 않은 토큰 ->{}", e.getMessage());
      return false;
    }
  }

  //AccessToken에서 email추출
  public Optional<String> extractEmail(String accessToken) {
    try {
      return Optional.ofNullable(JWT.require(Algorithm.HMAC512(secretKey))
          .build()
          .verify(accessToken)
          .getClaim("email")
          .asString()
      );
    } catch (Exception e) {
      log.error("유효하지 않은 AccessToken");
      return Optional.empty();
    }
  }
}
