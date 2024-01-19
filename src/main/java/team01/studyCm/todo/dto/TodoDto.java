package team01.studyCm.todo.dto;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import team01.studyCm.chat.entity.Chat;
import team01.studyCm.user.entity.User;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoDto {

    private Long toDoId;
    private String email;
    private Long chatId;
    private Boolean finish;
    private String contents;
    private LocalDateTime created_at;
    private LocalDateTime modified_at;
}

