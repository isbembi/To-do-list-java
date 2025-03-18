package kg.alatoo.todolist.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String title;

    @Column(nullable=false)
    private String description;

    @Column(nullable=false)
    private boolean completed;

    @Column(nullable=false)
    private LocalDateTime deadline;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Task() {}

    public Task(Long id, String title, String description, boolean completed, LocalDateTime deadline, User user) {
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
    public void setDeadline(LocalDateTime deadline) { this.deadline = deadline; }
    public void setUser(User user) { this.user = user; }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public boolean isCompleted() { return completed; }
    public LocalDateTime getDeadline() { return deadline; }
    public User getUser() { return user; }
}
