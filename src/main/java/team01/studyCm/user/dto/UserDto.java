package team01.studyCm.user.dto;

import lombok.*;
import team01.studyCm.user.entity.status.Role;

import java.time.LocalDateTime;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDto {
    private Long user_id;
    private String password;
    private String userName;
    private String job;
    private String phone;
    private Role role;
    private LocalDateTime created_at;
    private LocalDateTime modified_at;
    private String email;
}
