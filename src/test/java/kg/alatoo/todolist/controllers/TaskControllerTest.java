package kg.alatoo.todolist.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import kg.alatoo.todolist.dto.TaskDTO;
import kg.alatoo.todolist.entities.Task;
import kg.alatoo.todolist.entities.User;
import kg.alatoo.todolist.mappers.TaskMapper;
import kg.alatoo.todolist.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TaskService taskService;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskController taskController;

    private TaskDTO taskDTO;
    private Task task;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
        taskDTO = new TaskDTO(1L, "Тестовая задача", "Описание задачи", false, LocalDate.now().plusDays(1), 1L);
        task = new Task(1L, "Тестовая задача", "Описание задачи", false, LocalDate.now().plusDays(1), new User());
    }

    @Test
    void testGetTaskById_NotFound() throws Exception {
        when(taskService.getTaskById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/tasks/1"))
                .andExpect(status().isNotFound());
    }


}
