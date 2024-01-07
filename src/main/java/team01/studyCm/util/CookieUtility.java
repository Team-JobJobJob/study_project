package team01.studyCm.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;
import team01.studyCm.user.dto.UserDto;
import team01.studyCm.user.entity.status.Job;

@UtilityClass
public class CookieUtility {
    public void setUserCookie(HttpServletResponse response, UserDto user) {
        Cookie userEmail = new Cookie("user_email", user.getEmail());
        Cookie userName = new Cookie("user_name", user.getUserName());
        Cookie userJob = new Cookie("user_job", EnumUtility.jobEnumValueToName(user.getJob()));
        Cookie user_id = new Cookie("user_id", Long.toString(user.getUser_id()));

        userEmail.setPath("/");
        userName.setPath("/");
        userJob.setPath("/");
        user_id.setPath("/");

        userEmail.setMaxAge(24 * 60 * 60);
        userName.setMaxAge(24 * 60 * 60);
        userJob.setMaxAge(24 * 60 * 60);
        user_id.setMaxAge(24 * 60 * 60);

        response.addCookie(userEmail);
        response.addCookie(userName);
        response.addCookie(userJob);
        response.addCookie(user_id);
    }
}
