package team01.studyCm.user.dto;

import lombok.*;
import team01.studyCm.user.entity.User;
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

    public static UserDto toUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUser_id(user.getUser_id());
        userDto.setUserName(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setPhone(user.getPhone());
        userDto.setJob(user.getJob());

        return userDto;
    }
}
