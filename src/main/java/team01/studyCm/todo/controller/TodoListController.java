package team01.studyCm.todo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import team01.studyCm.chat.service.ChatService;
import team01.studyCm.todo.dto.TodoDto;
import team01.studyCm.todo.entity.TodoList;
import team01.studyCm.todo.service.TodoListService;
import team01.studyCm.user.service.UserService;

import java.security.Principal;

@RequestMapping("/todoList/{chatId}")
@RequiredArgsConstructor
@Controller
public class TodoListController {

    private final TodoListService todoListService;

    @GetMapping("")
    public String getList(Model model, @RequestParam Long chatId) {
        model.addAttribute(todoListService.getAllByChatId(chatId));
        return "todo/list";
    }

    @GetMapping("/modify")
    public String modifyListPage() {
        return "todoList/modify";
    }

    @PostMapping("/modify")
    public String modifyList(Model model, TodoDto todoDto) {
        boolean success = todoListService.modifyTodo(todoDto);

        if(!success) {
            return "todoList/modify";
        }

        return "todoList/list";
    }

    @GetMapping("/add")
    public String addListPage(Model model, TodoDto todoDto) {
        model.addAttribute(todoDto);
        return "todoList/add";
    }

    @PostMapping("/add")
    public String addList(Model model, TodoDto todoDto) {
        boolean success = todoListService.addTodo(todoDto);

        if(!success) {
            return "todoList/add";
        }

        return "todoList/list";
    }

    @DeleteMapping("")
    public String deleteList(Long toDoId) {
        todoListService.deleteTodo(toDoId);
        return "redirect:todoList/list";
    }

}
