package team01.studyCm.todo;

import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import team01.studyCm.todo.dto.CheckBoxDto;
import team01.studyCm.todo.entity.TodoList;
import team01.studyCm.todo.service.TodoListService;
import team01.studyCm.user.dto.UserDto;
import team01.studyCm.user.dto.UserInfoDto;
import team01.studyCm.user.service.UserDetailService;
import team01.studyCm.user.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TodoControllerTest {
    @Mock
    private TodoListService todoListService;

    @Autowired
    private MockMvc mockMvc;

    private CheckBoxDto checkBoxDto;
    private MockHttpServletRequest request;

    @Test
    void testTodoPost() throws Exception {
        // Given

        given(todoListService.updateCheckTodo(any(String.class), any(String.class), any(Boolean.class)))
                .willReturn(true);

        // When/Then
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/chat/1/todoList/check")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"request\": \"null\", \"authentication\": \"null\", \"chatId\": \"chatId\"," +
                        " \"contents\": \"hi\" , \"finish\": \"true\"}"))
                .andExpect(status().is3xxRedirection()) // Expect a redirect status code
                .andExpect(redirectedUrl("/chat/1/todoList"))
                .andReturn();
    }

    @Test
    void testTodoAdd() throws Exception {
        // Given
        List<String> list = new ArrayList<>();

        given(todoListService.getAllUniqueEmailByChatId(any(Long.class)))
                .willReturn(list);

        // When/Then
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/chat/1/todoList/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"chatId\": \"chatId\"," +
                        " \"email\": \"test1@gmail.com\"}"))
                .andExpect(status().is3xxRedirection()) // Expect a redirect status code
                .andExpect(redirectedUrl("/chat/1/todoList"))
                .andReturn();
    }

    @Test
    public void testDeleteList() throws Exception {
        // Given
        Long chatId = 42L;
        Long toDoId = 1L;

        doNothing().when(todoListService).deleteTodo(toDoId);

        // When and Then
        mockMvc.perform(delete("/chat/{chatId}/todoList", chatId)
                .param("toDoId", String.valueOf(toDoId)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/chat/" + chatId + "/todoList"));
    }
}
