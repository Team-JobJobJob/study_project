package team01.studyCm.alarm.handler;

import java.nio.file.AccessDeniedException;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import team01.studyCm.config.TokenProvider;
import team01.studyCm.user.entity.User;
import team01.studyCm.user.repository.UserRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlarmHandler implements ChannelInterceptor {
  private final TokenProvider tokenProvider;
  private final UserRepository userRepository;

  //connect 시 토큰 검증
  @Override
  public Message<?> preSend(Message<?> message, MessageChannel channel){
    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

    if(accessor.getCommand() == StompCommand.CONNECT){
      String jwt = accessor.getFirstNativeHeader("token");
      if(tokenProvider.isTokenValid(jwt)){
        jwt = jwt.substring(6, jwt.length());
      }
      Long userId = tokenProvider.getUserIdFromAccessToken(jwt);

      if (userId != null) {
        log.debug("userId: {}", userId);
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
          return message;
        }
      }
    }

    return message;
  }

  public void postSend(Message message, MessageChannel channel, boolean sent) {
    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
    String sessionId = accessor.getSessionId();
    switch (accessor.getCommand()) {
      case CONNECT:

        break;
      case DISCONNECT:
        log.info("DISCONNECT");
        log.info("sessionId : {}", sessionId);
        log.info("channel : {}", channel);
        break;

        default:
          break;

    }

  }


}
