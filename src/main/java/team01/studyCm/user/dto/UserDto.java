package team01.studyCm.user.dto;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDto {
    private Long user_id;
    private String id;
    private String password;
    private String user_name;
    private String job;
    private String phone;
    private LocalDateTime created_at;
    private LocalDateTime modified_at;
    private String email;
}
