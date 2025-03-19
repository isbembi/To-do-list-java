package kg.alatoo.todolist.mappers;

import kg.alatoo.todolist.dto.TaskDTO;
import kg.alatoo.todolist.entities.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public TaskDTO toDTO(Task task) {
        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.isCompleted(),
                task.getDeadline(),
                task.getUser().getId()
        );
    }

    public Task toEntity(TaskDTO taskDTO) {
        Task task = new Task();
        task.setId(taskDTO.getId());
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setCompleted(taskDTO.isCompleted());
        task.setDeadline(taskDTO.getDeadline());
        return task;
    }
}
