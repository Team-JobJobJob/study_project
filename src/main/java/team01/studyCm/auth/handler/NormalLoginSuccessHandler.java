//package team01.studyCm.auth.handler;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import team01.studyCm.auth.CustomOAuth2User;
//import team01.studyCm.user.entity.status.Role;
//
//import java.io.IOException;
//
//public class NormalLoginSuccessHandler implements AuthenticationSuccessHandler {
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request,
//                                        HttpServletResponse response,
//                                        FilterChain chain,
//                                        Authentication authentication)
//            throws IOException, ServletException {
//
//        log.info("OAuth2 로그인 성공");
//        try{
//            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
//            String email = oAuth2User.getEmail();
//
//            //처음 로그인하는 경우 GUEST로 role 설정, 추가 정보 입력받도록 redirect
//            if(oAuth2User.getRole().equals(Role.GUEST)){
//                String accessToken = tokenProvider.createAccessToken(oAuth2User.getEmail());
//                response.addHeader("Authorization","Bearer "+accessToken);
//                response.sendRedirect("/oauth2/signup");
//            }else{
//                loginSuccess(response, oAuth2User);
//                response.sendRedirect("");
//            }
//        }catch (Exception e){
//            throw e;
//        }
//    }
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//
//    }
//}
