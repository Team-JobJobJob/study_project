package team01.studyCm.alarm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import team01.studyCm.alarm.service.AlarmService;

@RestController
@RequiredArgsConstructor
public class AlarmController {
  private final SimpMessageSendingOperations messageSendingOperations;


  @GetMapping(value = "/alarm/chat")
  public String stompAlarm(){
    return "/stomp";
  }

  @MessageMapping("/{userId}")
  public void message(@DestinationVariable("userId") Long userId){
    messageSendingOperations.convertAndSend("/sub/" + userId, "alarm connection completed.");
  }

}
