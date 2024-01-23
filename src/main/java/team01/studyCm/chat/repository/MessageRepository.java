package team01.studyCm.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team01.studyCm.chat.entity.Message;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
//    List<Message> findAllByChatId(Long chatId);
}
