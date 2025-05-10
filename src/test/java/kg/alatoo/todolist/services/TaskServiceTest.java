package kg.alatoo.todolist.services;

import kg.alatoo.todolist.entities.Task;
import kg.alatoo.todolist.entities.User;
import kg.alatoo.todolist.repositories.TaskRepository;
import kg.alatoo.todolist.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User(1L, "Alice", "alice@example.com", null, "test123");
        task = new Task(1L, "Тестовая задача", "Описание задачи", false, LocalDate.now().plusDays(1), user);
    }

    @Test
    void testCreateTask_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task createdTask = taskService.createTask(task, 1L);

        assertThat(createdTask).isNotNull();
        assertThat(createdTask.getTitle()).isEqualTo("Тестовая задача");

        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void testCreateTask_UserNotFound() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> taskService.createTask(task, 2L));

        assertThat(exception.getMessage()).isEqualTo("User not found with id: 2");
    }

    @Test
    void testMarkTaskAsCompleted_Success() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task completedTask = taskService.markTaskAsCompleted(1L);

        assertThat(completedTask.isCompleted()).isTrue();
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void testMarkTaskAsCompleted_TaskNotFound() {
        when(taskRepository.findById(2L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> taskService.markTaskAsCompleted(2L));

        assertThat(exception.getMessage()).isEqualTo("Task not found with id: 2");
    }

    @Test
    void testDeleteTask() {
        doNothing().when(taskRepository).deleteById(1L);

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).deleteById(1L);
    }
}
