package team01.studyCm.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team01.studyCm.chat.entity.Chat;
import team01.studyCm.user.entity.User;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findAllByUser(User user);

    Chat findByEmail(String email);

}
