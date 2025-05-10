//package kg.alatoo.todolist.bootstrap;
//
//import kg.alatoo.todolist.entities.Role;
//import kg.alatoo.todolist.entities.Task;
//import kg.alatoo.todolist.entities.User;
//import kg.alatoo.todolist.repositories.TaskRepository;
//import kg.alatoo.todolist.repositories.UserRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.util.Optional;
//
//@Component
//public class DataInit implements CommandLineRunner {
//
//    private final UserRepository userRepository;
//    private final TaskRepository taskRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    public DataInit(UserRepository userRepository,
//                    TaskRepository taskRepository,
//                    PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.taskRepository = taskRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Override
//    public void run(String... args) {
//        createUserIfNotExists("Bermet", "bem@gmail.com", "password123", Role.ADMIN);
//        createUserIfNotExists("Aisuluu", "aisuluuu@gmail.com", "secure456", Role.USER);
//
//        Optional<User> user1 = userRepository.findByEmail("bem@gmail.com");
//        Optional<User> user2 = userRepository.findByEmail("aisuluuu@gmail.com");
//
//        user1.ifPresent(u -> {
//            Task task = new Task();
//            task.setTitle("Complete Back-end project");
//            task.setDescription("Finish the to-do list application");
//            task.setCompleted(false);
//            task.setDeadline(LocalDate.now().plusDays(3));
//            task.setUser(u);
//            taskRepository.save(task);
//        });
//
//        user2.ifPresent(u -> {
//            Task task = new Task();
//            task.setTitle("Study for History exam");
//            task.setDescription("Prepare for the test <<Ishak Razakov>>");
//            task.setCompleted(false);
//            task.setDeadline(LocalDate.now().plusDays(5));
//            task.setUser(u);
//            taskRepository.save(task);
//        });
//
//        System.out.println("✅ Initial users and tasks loaded (if not already present).");
//    }
//
//    private void createUserIfNotExists(String name, String email, String rawPassword, Role role) {
//        if (userRepository.findByEmail(email).isEmpty()) {
//            User user = new User();
//            user.setName(name);
//            user.setEmail(email);
//            user.setPassword(passwordEncoder.encode(rawPassword));
//            user.setRole(role);
//            userRepository.save(user);
//            System.out.println("👤 User " + name + " created.");
//        } else {
//            System.out.println("⚠️ User with email " + email + " already exists. Skipping.");
//        }
//    }
//}
