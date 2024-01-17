package team01.studyCm.todo.service;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team01.studyCm.auth.service.CustomOAuth2UserService;
import team01.studyCm.chat.entity.Chat;
import team01.studyCm.todo.dto.TodoDto;
import team01.studyCm.todo.entity.TodoList;
import team01.studyCm.todo.repository.TodoRepository;
import team01.studyCm.user.dto.UserDto;
import team01.studyCm.user.entity.User;
import team01.studyCm.user.entity.status.Role;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TodoListService {

    private final TodoRepository todoRepository;

    public boolean addTodo(TodoDto todoDto) {
        Optional<TodoList> optionalTodoList = todoRepository.findByEmailAndChatId(todoDto.getEmail(), todoDto.getChatId());
        if(optionalTodoList.isPresent()){
            return false;
        }

        TodoList todo = TodoList.builder()
                .email(todoDto.getEmail())
                .chatId(todoDto.getChatId())
                .created_at(todoDto.getCreated_at())
                .modified_at(todoDto.getModified_at())
                .contents(todoDto.getContents())
                .finish(todoDto.getFinish())
                .toDoId(todoDto.getToDoId())
                .build();

        todoRepository.save(todo);

        return true;
    }

    public void deleteTodo(Long toDoId) {
        Optional<TodoList> optionalChatRoom = todoRepository.findById(toDoId);

        if(optionalChatRoom.isEmpty()){
            return ;
        }

        todoRepository.delete(optionalChatRoom.get());
    }

    public boolean modifyTodo(TodoDto todoDto) {
        Optional<TodoList> optionalTodoList = todoRepository.findByEmailAndChatId(todoDto.getEmail(), todoDto.getChatId());
        if(optionalTodoList.isPresent()){
            return false;
        }

        TodoList todo = optionalTodoList.get();

        todo.setContents(todoDto.getContents());
        todo.setFinish(todoDto.getFinish());

        todoRepository.save(todo);

        return true;
    }

    public List<TodoList> getAllByChatId(Long chatId) {
        return todoRepository.findByChatId(chatId);
    }

}
