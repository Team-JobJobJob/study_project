package team01.studyCm.chat.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team01.studyCm.chat.entity.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findAllByUser_Email(String email);

    Page<Chat> findByJob(String job, Pageable pageable);

    Chat findByChatId(Long ChatId);
}
