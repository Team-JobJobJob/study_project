package team01.studyCm.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team01.studyCm.chat.entity.Chat;
import team01.studyCm.todo.entity.TodoList;
import team01.studyCm.user.entity.User;

import java.util.List;
import java.util.Optional;


@Repository
public interface TodoRepository extends JpaRepository<TodoList, Long> {
    Optional<TodoList> findByEmailAndContents(String email, String content);
    List<TodoList> getUniqueContentsByChatId(Long chatId);
    List<TodoList> getUniqueEmailsByChatId(Long chatId);
    List<TodoList> findByEmailAndChatId(String email, Long chatId);
}
