package team01.studyCm.alarm.service;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import team01.studyCm.alarm.controller.AlarmController;
import team01.studyCm.chat.dto.MessageDto;
import team01.studyCm.chat.repository.ChatRepository;
import team01.studyCm.user.entity.User;
import team01.studyCm.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AlarmService {
  private final SimpMessageSendingOperations messageSendingOperations;

  public void alarmMessage(MessageDto messageDto){
    messageSendingOperations.convertAndSend("/sub/"+ messageDto.getReceiverId(), messageDto);
  }


}
