package team01.studyCm.alarm.handler;

import java.nio.file.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import team01.studyCm.config.TokenProvider;

@Component
@RequiredArgsConstructor
public class AlarmHandler implements ChannelInterceptor {
  private final TokenProvider tokenProvider;

  //connect 시 토큰 검증
  @Override
  public Message<?> preSend(Message<?> message, MessageChannel channel){
    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
    if(accessor.getCommand() == StompCommand.CONNECT){
      if(!tokenProvider.isTokenValid(accessor.getFirstNativeHeader("token"))){
        throw new RuntimeException("Access Deny");
      }
    }

    return message;
  }


}
