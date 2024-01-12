package team01.studyCm.alarm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import team01.studyCm.chat.entity.Chat;
import team01.studyCm.user.entity.User;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Alarm {
  @Id
  @GeneratedValue(strategy =  GenerationType.IDENTITY)
  @Column(name = "alarm_id")
  private Long alarmId;

  @ManyToOne
  @JoinColumn(name = "chat")
  private Chat chat;

  @Column(name = "user_id")
  private Long userId;
  private String message;

  @CreatedDate
  private LocalDateTime createdAt;

}
