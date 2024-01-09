package team01.studyCm.chat.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import team01.studyCm.chat.dto.ChatDto;
import team01.studyCm.chat.dto.MessageDto;
import team01.studyCm.chat.entity.Chat;
import team01.studyCm.chat.service.ChatService;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final ChatService chatService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload {}", payload);
//        TextMessage textMessage = new TextMessage("welcome chat server");
//        session.sendMessage(textMessage);
        MessageDto messageDto = objectMapper.readValue(payload, MessageDto.class);

    }

}
