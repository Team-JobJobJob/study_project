package team01.studyCm.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import team01.studyCm.auth.handler.OAuth2LoginFailureHandler;
import team01.studyCm.auth.handler.OAuth2LoginSuccessHandler;
import team01.studyCm.auth.service.CustomOAuth2UserService;
import team01.studyCm.user.handler.UserLoginFailureHandler;
import team01.studyCm.user.handler.UserLoginSuccessHandler;
import team01.studyCm.user.repository.UserRepository;
import team01.studyCm.user.service.UserDetailService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

  //private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final CustomOAuth2UserService customOAuth2UserService;
  private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
  private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
  private final UserDetailService userDetailService;
  private final UserRepository userRepository;
  private final TokenProvider tokenProvider;
  private final ObjectMapper objectMapper;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.httpBasic(AbstractHttpConfigurer::disable)
        .csrf(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(requests -> requests
            .requestMatchers(new AntPathRequestMatcher("/**")).permitAll()
            .anyRequest().authenticated()
        )
        .oauth2Login(oauth2 -> oauth2
            .successHandler(oAuth2LoginSuccessHandler)
            .failureHandler(oAuth2LoginFailureHandler)
            .userInfoEndpoint(userInfoEndpointConfig ->
                userInfoEndpointConfig.userService(customOAuth2UserService))
        )
        .addFilterAfter(authenticationProcessingFilter(), LogoutFilter.class)
        .addFilterBefore(jwtAuthenticationFilter(), AuthenticationProcessingFilter.class);

    return http.build();
  }


  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(passwordEncoder());
    provider.setUserDetailsService(userDetailService);
    return new ProviderManager(provider);
  }

  @Bean
  public UserLoginSuccessHandler userLoginSuccessHandler() {
    return new UserLoginSuccessHandler(userRepository,tokenProvider);
  }

  @Bean
  public UserLoginFailureHandler userLoginFailureHandler() {
    return new UserLoginFailureHandler();
  }

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter(){
    JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(tokenProvider, userRepository);
    return jwtAuthenticationFilter;
  }
  @Bean
  public AuthenticationProcessingFilter authenticationProcessingFilter() {
    AuthenticationProcessingFilter authenticationProcessingFilter
        = new AuthenticationProcessingFilter(objectMapper);
    authenticationProcessingFilter.setAuthenticationManager(authenticationManager());
    authenticationProcessingFilter.setAuthenticationSuccessHandler(
        userLoginSuccessHandler());
    authenticationProcessingFilter.setAuthenticationFailureHandler(userLoginFailureHandler());
    return authenticationProcessingFilter;
  }



}
