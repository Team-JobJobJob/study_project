package team01.studyCm.alarm.handler;

import java.nio.file.AccessDeniedException;
import java.util.Objects;
import java.util.Optional;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
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
//  @Override
//  public Message<?> preSend(Message<?> message, MessageChannel channel){
//    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//
//    if(accessor.getCommand() == StompCommand.CONNECT){
//      String jwt = accessor.getFirstNativeHeader("token");
//      if(tokenProvider.isTokenValid(jwt)){
//        jwt = jwt.substring(6, jwt.length());
//      }
//      Long userId = tokenProvider.getUserIdFromAccessToken(jwt);
//
//      if (userId != null) {
//        log.debug("userId: {}", userId);
//        Optional<User> user = userRepository.findById(userId);
//        if(user.isPresent()) {
//          return message;
//        }
//      }
//    }
//
//    return message;
//  }


  @Override
  public void postSend(Message message, MessageChannel channel, boolean sent) {
    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
    String sessionId = accessor.getSessionId();
    switch (accessor.getCommand()) {
      case CONNECT:
        // 유저가 Websocket으로 connect()를 한 뒤 호출됨

        break;
      case DISCONNECT:
        log.info("DISCONNECT");
        log.info("sessionId: {}",sessionId);
        log.info("channel:{}",channel);
        // 유저가 Websocket으로 disconnect() 를 한 뒤 호출됨 or 세션이 끊어졌을 때 발생함(페이지 이동~ 브라우저 닫기 등)
        break;
      default:
        break;
    }

  }


}
