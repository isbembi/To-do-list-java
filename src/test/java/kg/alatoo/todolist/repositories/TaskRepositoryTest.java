package kg.alatoo.todolist.repositories;

import kg.alatoo.todolist.entities.Task;
import kg.alatoo.todolist.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private Task task;

    @BeforeEach
    void setUp() {
        user = new User(null, "Alice", "alice@example.com", null);
        user = userRepository.save(user);

        task = new Task(null, "Тестовая задача", "Описание", false, LocalDate.now().plusDays(1), user);
        task = taskRepository.save(task);
    }

    @Test
    void testFindByUserId() {
        List<Task> tasks = taskRepository.findByUserId(user.getId());

        assertThat(tasks).isNotEmpty();
        assertThat(tasks.get(0).getTitle()).isEqualTo("Тестовая задача");
    }

    @Test
    void testFindById_Success() {
        Optional<Task> foundTask = taskRepository.findById(task.getId());

        assertThat(foundTask).isPresent();
        assertThat(foundTask.get().getTitle()).isEqualTo("Тестовая задача");
    }

    @Test
    void testFindById_NotFound() {
        Optional<Task> foundTask = taskRepository.findById(999L);

        assertThat(foundTask).isEmpty();
    }

    @Test
    void testDeleteTask() {
        taskRepository.delete(task);

        Optional<Task> foundTask = taskRepository.findById(task.getId());
        assertThat(foundTask).isEmpty();
    }
}
