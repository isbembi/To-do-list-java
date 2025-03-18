package kg.alatoo.todolist.services;


import kg.alatoo.todolist.entities.Task;
import kg.alatoo.todolist.entities.User;
import kg.alatoo.todolist.repositories.TaskRepository;
import kg.alatoo.todolist.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {


    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public List<Task> getTasksByUserId(Long userId) {
        return taskRepository.findByUserId(userId);
    }

    public Task createTask(Task task, Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            task.setUser(user.get());
            return taskRepository.save(task);
        } else {
            throw new RuntimeException("User not found with id: " + userId);
        }
    }
    public Task updateTask(Long taskId, Task updatedTask) {
        return taskRepository.findById(taskId)
                .map(task -> {
                    task.setTitle(updatedTask.getTitle());
                    task.setDescription(updatedTask.getDescription());
                    task.setCompleted(updatedTask.isCompleted());
                    task.setDeadline(updatedTask.getDeadline());
                    return taskRepository.save(task);
                })
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));
    }
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

}
