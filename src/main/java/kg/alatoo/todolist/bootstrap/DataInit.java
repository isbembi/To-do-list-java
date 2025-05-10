package kg.alatoo.todolist.bootstrap;

import kg.alatoo.todolist.entities.Task;
import kg.alatoo.todolist.entities.User;
import kg.alatoo.todolist.repositories.TaskRepository;
import kg.alatoo.todolist.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInit implements CommandLineRunner {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInit(UserRepository userRepository,
                    TaskRepository taskRepository,
                    PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        User user1 = new User();
        user1.setName("Bermet");
        user1.setEmail("bem@gmail.com");
        user1.setPassword(passwordEncoder.encode("password123"));
        userRepository.save(user1);

        User user2 = new User();
        user2.setName("Aisuluu");
        user2.setEmail("aisuluuu@gmail.com");
        user2.setPassword(passwordEncoder.encode("secure456"));
        userRepository.save(user2);

        Task task1 = new Task();
        task1.setTitle("Complete Back-end project");
        task1.setDescription("Finish the to-do list application");
        task1.setCompleted(false);
        task1.setDeadline(LocalDate.now().plusDays(3));
        task1.setUser(user1);
        taskRepository.save(task1);

        Task task2 = new Task();
        task2.setTitle("Study for History exam");
        task2.setDescription("Prepare for the test <<Ishak Razakov>>");
        task2.setCompleted(false);
        task2.setDeadline(LocalDate.now().plusDays(5));
        task2.setUser(user2);
        taskRepository.save(task2);

        System.out.println("Test data with encoded passwords loaded!");
    }
}
