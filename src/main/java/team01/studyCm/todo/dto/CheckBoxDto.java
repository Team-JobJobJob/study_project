package team01.studyCm.todo.dto;

import lombok.*;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckBoxDto {
    private Boolean finish;
    private String contents;
}


