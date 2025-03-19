package kg.alatoo.todolist.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="Header cannot be empty")
    @Column(nullable=false, unique=true)
    private String title;

    @NotBlank(message = "The description cannot be empty")
    @Column(nullable=false)
    private String description;

    @NotNull(message="Completed field cannot be empty")
    @Column(nullable=false)
    private boolean completed;



    @NotNull(message = "Deadline mast be filled")
    @FutureOrPresent(message = "Should deadline be in the past or in the future")
    @Column(nullable=false)
    private LocalDate deadline;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Task() {}

    public Task(Long id, String title, String description, boolean completed, LocalDate deadline, User user) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.deadline = deadline;
        this.user = user;
    }

    // Явные сеттеры (если Lombok не работает)
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setCompleted(boolean completed) { this.completed = completed; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }
    public void setUser(User user) { this.user = user; }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public boolean isCompleted() { return completed; }
    public LocalDate getDeadline() { return deadline; }
    public User getUser() { return user; }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
}
