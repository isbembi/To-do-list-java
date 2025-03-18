package kg.alatoo.todolist.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @Column(nullable=false, unique=true)
    private String description;

    @Column(nullable=false)
    private boolean completed;

    @Column(nullable=false)
    private String deadline;


}


