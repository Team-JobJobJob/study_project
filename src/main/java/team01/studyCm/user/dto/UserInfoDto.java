package team01.studyCm.user.dto;

import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserInfoDto {
    private String userName;
    private String password;
    private String job;
    private String phone;
}
