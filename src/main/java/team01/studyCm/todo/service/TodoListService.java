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
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TodoListService {

    private final TodoRepository todoRepository;

    public boolean addTodo(TodoDto todoDto) {
        LocalDateTime currentTime = LocalDateTime.now();

        TodoList todo = TodoList.builder()
                .email(todoDto.getEmail())
                .chatId(todoDto.getChatId())
                .created_at(currentTime)
                .modified_at(currentTime)
                .contents(todoDto.getContents())
                .finish(false)
                .build();

        todoRepository.save(todo);

        return true;
    }

    public boolean addTodoWithElements(String email, Long chatId, String contents) {
        LocalDateTime currentTime = LocalDateTime.now();

        TodoList todo = TodoList.builder()
                .email(email)
                .chatId(chatId)
                .contents(contents)
                .created_at(currentTime)
                .modified_at(currentTime)
                .finish(false)
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

    public List<String> getAllUniqueEmailByChatId(Long chatId) {
        List<TodoList> lst = todoRepository.getUniqueEmailsByChatId(chatId);
        List<String> emails = lst.stream()
                .map(TodoList::getEmail)
                .collect(Collectors.toList());
        Set<String> uniqueSet = new HashSet<>(emails);
        return new ArrayList<>(uniqueSet);
    }

//    public boolean modifyTodo(TodoDto todoDto) {
//        Optional<TodoList> optionalTodoList = todoRepository.findByEmailAndChatId(todoDto.getEmail(), todoDto.getChatId());
//        if(optionalTodoList.isPresent()){
//            return false;
//        }
//
//        TodoList todo = optionalTodoList.get();
//
//        todo.setContents(todoDto.getContents());
//        todo.setFinish(todoDto.getFinish());
//
//        todoRepository.save(todo);
//
//        return true;
//    }

    public boolean updateCheckTodo(String content, String email, boolean finish) {
        Optional<TodoList> optionalTodoList = todoRepository.findByEmailAndContents(email, content);
        if(!optionalTodoList.isPresent()){
            return false;
        }
        TodoList todo = optionalTodoList.get();
        todo.setFinish(!finish);
        todoRepository.save(todo);

        return true;
    }

    public List<String> getUniqueContentsByChatId(Long chatId) {
        List<TodoList> lst = todoRepository.getUniqueContentsByChatId(chatId);
        List<String> contents = lst.stream()
                .map(TodoList::getContents)
                .collect(Collectors.toList());
        Set<String> uniqueSet = new HashSet<>(contents);
        return new ArrayList<>(uniqueSet);
    }

    public List<TodoList> findWithEmailAndChatId(Long chatId, String email) {
        return todoRepository.findByEmailAndChatId(email, chatId);
    }

    public Boolean hasTodo(Long chatId, String email) {
        return todoRepository.findByEmailAndChatId(email, chatId).size() > 0;
    }
}
