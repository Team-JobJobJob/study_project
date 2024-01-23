package team01.studyCm.todo.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import team01.studyCm.auth.CustomOAuth2User;
import team01.studyCm.chat.service.ChatService;
import team01.studyCm.todo.dto.CheckBoxDto;
import team01.studyCm.todo.dto.TodoDto;
import team01.studyCm.todo.entity.TodoList;
import team01.studyCm.todo.service.TodoListService;
import team01.studyCm.user.service.UserService;
import team01.studyCm.util.CookieUtility;

import java.security.Principal;
import java.util.*;

@RequestMapping("/todoList")
@RequiredArgsConstructor
@Controller
@Slf4j
public class TodoListController {

    private final TodoListService todoListService;

    @GetMapping("/{chatId}")
    public String getList(@PathVariable Long chatId, Model model, HttpServletRequest request, Authentication authentication) {
        log.info("투 두 입장");
        String email;
        if(authentication != null) {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
            email = oAuth2User.getEmail();
        }
        else{
            Map<String, String> map = CookieUtility.getCookie(request);
            email = map.get("userEmail");
        }
    List<String> comments = todoListService.getUniqueContentsByChatId(chatId);

    Boolean hasTodo = todoListService.hasTodo(chatId, email);
        if(!hasTodo) {
        for(String comment: comments) {
            todoListService.addTodoWithElements(email, chatId, comment);
        }
    }

        model.addAttribute("todoList", todoListService.findWithEmailAndChatId(chatId, email));
        model.addAttribute("chatId", chatId);
        return "todo/list";
    }

    @PostMapping("/check/{chatId}")
    public String checkList(HttpServletRequest request, Authentication authentication, @PathVariable Long chatId, @RequestBody CheckBoxDto checkBoxDto) {
        String email;
        if(authentication != null) {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
            email = oAuth2User.getEmail();
        }
        else{
            Map<String, String> map = CookieUtility.getCookie(request);
            email = map.get("userEmail");
        }

        log.info("투 두 업데이트");
        log.info(checkBoxDto.getContents());
        log.info(String.valueOf(checkBoxDto.getFinish()));

        todoListService.updateCheckTodo(checkBoxDto.getContents(), email, checkBoxDto.getFinish());
        return "redirect:/todoList/" + chatId;
    }

    @GetMapping("/add/{chatId}")
    public String addListPage(@PathVariable Long chatId, Model model, HttpServletRequest request, Authentication authentication) {
        String email;
        if(authentication != null) {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
            email = oAuth2User.getEmail();
        }
        else{
            Map<String, String> map = CookieUtility.getCookie(request);
            email = map.get("userEmail");
        }
        model.addAttribute("chatId", chatId);
        model.addAttribute("email", email);
        return "todo/add";
    }

    @PostMapping("/add/{chatId}")
    public String addList(@PathVariable Long chatId, TodoDto todoDto) {
        List<String> emails = todoListService.getAllUniqueEmailByChatId(chatId);
        String currentEmail = todoDto.getEmail();
        todoListService.addTodo(todoDto);

        for(String email: emails) {
            if(!currentEmail.equals(email)) {
                todoDto.setEmail(email);
                todoListService.addTodo(todoDto);
            }
        }

        return "redirect:/todoList/" + chatId;
    }

    @DeleteMapping("")
    public String deleteList(Long toDoId) {
        todoListService.deleteTodo(toDoId);
        return "redirect:todo/list";
    }

    // 변경 예정   /todolist/+chatId

}
