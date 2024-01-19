package team01.studyCm.todo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import team01.studyCm.chat.dto.ChatDto;
import team01.studyCm.chat.entity.Chat;
import team01.studyCm.todo.dto.TodoDto;
import team01.studyCm.user.entity.User;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "todo")
public class TodoList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long toDoId;

    private String email;

    private Long chatId;

    private Boolean finish;

    private String contents;

    @CreatedDate
    @Column
    private LocalDateTime created_at;

    @LastModifiedDate
    @Column
    private LocalDateTime modified_at;

    public static TodoList toEntity(TodoDto todoDto) {
        return TodoList.builder()
                .toDoId(todoDto.getToDoId())
                .email(todoDto.getEmail())
                .chatId(todoDto.getChatId())
                .finish(todoDto.getFinish())
                .contents(todoDto.getContents())
                .created_at(LocalDateTime.now())
                .modified_at(LocalDateTime.now())
                .build();
    }
}
