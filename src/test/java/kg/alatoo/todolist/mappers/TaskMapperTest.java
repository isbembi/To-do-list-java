package kg.alatoo.todolist.mappers;

import kg.alatoo.todolist.dto.TaskDTO;
import kg.alatoo.todolist.entities.Task;
import kg.alatoo.todolist.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class TaskMapperTest {

    private TaskMapper taskMapper;
    private Task task;
    private TaskDTO taskDTO;
    private User user;

    @BeforeEach
    void setUp() {
        taskMapper = new TaskMapper();

        user = new User(1L, "Alice", "alice@example.com", null);
        task = new Task(1L, "Тестовая задача", "Описание задачи", false, LocalDate.now().plusDays(1), user);
        taskDTO = new TaskDTO(1L, "Тестовая задача", "Описание задачи", false, LocalDate.now().plusDays(1), 1L);
    }

    @Test
    void testToDTO() {
        TaskDTO mappedDTO = taskMapper.toDTO(task);

        assertThat(mappedDTO).isNotNull();
        assertThat(mappedDTO.getId()).isEqualTo(task.getId());
        assertThat(mappedDTO.getTitle()).isEqualTo(task.getTitle());
        assertThat(mappedDTO.getUserId()).isEqualTo(task.getUser().getId());
    }

    @Test
    void testToEntity() {
        Task mappedTask = taskMapper.toEntity(taskDTO);

        assertThat(mappedTask).isNotNull();
        assertThat(mappedTask.getId()).isEqualTo(taskDTO.getId());
        assertThat(mappedTask.getTitle()).isEqualTo(taskDTO.getTitle());
        assertThat(mappedTask.getUser()).isNull(); // User не маппится напрямую
    }
}
