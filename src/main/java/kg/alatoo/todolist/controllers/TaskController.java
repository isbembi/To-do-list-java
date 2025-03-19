package kg.alatoo.todolist.controllers;

import kg.alatoo.todolist.dto.TaskDTO;
import kg.alatoo.todolist.entities.Task;
import kg.alatoo.todolist.mappers.TaskMapper;
import kg.alatoo.todolist.services.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @GetMapping
    public List<TaskDTO> getAllTasks() {
        return taskService.getAllTasks().stream()
                .map(taskMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        Optional<Task> task = taskService.getTaskById(id);
        return task.map(t -> ResponseEntity.ok(taskMapper.toDTO(t)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public List<TaskDTO> getTasksByUserId(@PathVariable Long userId) {
        return taskService.getTasksByUserId(userId).stream()
                .map(taskMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO, @PathVariable Long userId) {
        Task task = taskMapper.toEntity(taskDTO);
        Task newTask = taskService.createTask(task, userId);
        return ResponseEntity.ok(taskMapper.toDTO(newTask));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        Task task = taskMapper.toEntity(taskDTO);
        Task updatedTask = taskService.updateTask(id, task);
        return ResponseEntity.ok(taskMapper.toDTO(updatedTask));
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<TaskDTO> markTaskAsCompleted(@PathVariable Long id) {
        Task updatedTask = taskService.markTaskAsCompleted(id);
        return ResponseEntity.ok(taskMapper.toDTO(updatedTask));
    }

    @PatchMapping("/{id}/incomplete")
    public ResponseEntity<TaskDTO> markTaskAsIncomplete(@PathVariable Long id) {
        Task updatedTask = taskService.markTaskAsIncomplete(id);
        return ResponseEntity.ok(taskMapper.toDTO(updatedTask));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

}