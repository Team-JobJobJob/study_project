package team01.studyCm.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  public static final String TOKEN_HEADER = "Authorization";
  public static final String TOKEN_PREFIX = "Barer";
  private final TokenProvider tokenProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String token = this.resolveTokenFromRequest(request);

    if(StringUtils.hasText(token) && this.tokenProvider.validateToken(token)){
      Authentication auth = this.tokenProvider.getAuthentication(token);
      SecurityContextHolder.getContext().setAuthentication(auth);

    }

    filterChain.doFilter(request,response);

  }

  private String resolveTokenFromRequest(HttpServletRequest request){
    String token = request.getHeader(TOKEN_HEADER);

    if(!ObjectUtils.isEmpty(token) && token.startsWith(TOKEN_PREFIX)){
      return token.substring(TOKEN_PREFIX.length());
    }

    return null;
  }
}
