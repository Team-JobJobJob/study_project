package team01.studyCm.config;


import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import team01.studyCm.auth.OAuth2UserService;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final OAuth2UserService oAuth2UserService;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(requests -> requests
            .requestMatchers(new AntPathRequestMatcher("/**")).permitAll()
            .anyRequest().authenticated()
        )
        .oauth2Login(oauth2Configurer -> oauth2Configurer
            .loginPage("/login")
            .successHandler(successHandler())
            .userInfoEndpoint()
            .userService(oAuth2UserService))
//        .formLogin(form -> form
//            .loginPage("/login")
//            .defaultSuccessUrl("/", true)
//            .permitAll()
//        )
        .logout(request ->
            request.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true))
        .addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public AuthenticationSuccessHandler successHandler() {
    return ((request, response, authentication) -> {
      DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) authentication.getPrincipal();

      String id = defaultOAuth2User.getAttributes().get("id").toString();
      String body = """
          {"id":"%s"}
          """.formatted(id);

      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.setCharacterEncoding(StandardCharsets.UTF_8.name());

      PrintWriter writer = response.getWriter();
      writer.println(body);
      writer.flush();
    });
  }

}
