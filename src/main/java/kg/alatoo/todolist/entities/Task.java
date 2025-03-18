package kg.alatoo.todolist.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="tasks")
public class Task {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable=false, unique=true)
    private String title;

    @Column
    private String description;

    @Column(nullable=false)
    private boolean completed;

    @Column(nullable=false)
    private LocalDateTime deadline;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Связь с User по user_id
    private User user;
}


