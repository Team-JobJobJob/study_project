package team01.studyCm.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;
import team01.studyCm.user.dto.UserDto;
import team01.studyCm.user.entity.status.Job;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class CookieUtility {
    public void setUserCookie(HttpServletResponse response, UserDto user) {
        Cookie userEmail = new Cookie("user_email", user.getEmail());
        Cookie userName = new Cookie("user_name", user.getUserName());
        Cookie userJob = new Cookie("user_job", user.getJob());
//        Cookie user_id = new Cookie("user_id", Long.toString(user.getUser_id()));

        userEmail.setPath("/");
        userName.setPath("/");
        userJob.setPath("/");
//        user_id.setPath("/");

        userEmail.setMaxAge(24 * 60 * 60);
        userName.setMaxAge(24 * 60 * 60);
        userJob.setMaxAge(24 * 60 * 60);
//        user_id.setMaxAge(24 * 60 * 60);

        response.addCookie(userEmail);
        response.addCookie(userName);
        response.addCookie(userJob);
//        response.addCookie(user_id);
    }

    public void setJWT(HttpServletResponse response, String jwt) {
        Cookie userJWT = new Cookie("user_jwt", jwt);

        userJWT.setPath("/");

        userJWT.setMaxAge(24 * 60 * 60);

        response.addCookie(userJWT);
    }

    public Map<String, String> getCookie(HttpServletRequest request) {
        // Get all cookies from the request
        Cookie[] cookies = request.getCookies();

        Map<String, String> map = new HashMap<>();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("user_name".equals(cookie.getName())) {
                    map.put("userName", cookie.getValue());
                }
                if ("user_email".equals(cookie.getName())) {
                    map.put("userEmail", cookie.getValue());
                }
                if ("user_job".equals(cookie.getName())) {
                    map.put("userJob", cookie.getValue());
                }
                if ("user_jwt".equals(cookie.getName())) {
                    map.put("userJwt", cookie.getValue());
                }
            }
        }

        // You can now use the username as needed
        // ...

        return map;
    }
}
