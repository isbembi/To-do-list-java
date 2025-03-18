package kg.alatoo.todolist.controllers;


import jakarta.validation.Valid;
import kg.alatoo.todolist.entities.Task;
import kg.alatoo.todolist.services.TaskService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {


    private final TaskService taskService;

    public TaskController(final TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Optional<Task> task = taskService.getTaskById(id);
        return task.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public List<Task> getTasksByUserId(@PathVariable Long userId) {
        return taskService.getTasksByUserId(userId);
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<Task> createTask(@RequestBody @Valid Task task, @PathVariable Long userId) {
        try {
            Task newTask = taskService.createTask(task, userId);
            return ResponseEntity.ok(newTask);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    // Обновить задачу
    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable Long taskId, @RequestBody Task task) {
        try {
            Task updatedTask = taskService.updateTask(taskId, task);
            return ResponseEntity.ok(updatedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }



}
