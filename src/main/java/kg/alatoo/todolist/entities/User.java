package kg.alatoo.todolist.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name="users")
public class User {

    public  User(){}

    public User(Long id, String name, String email, List<Task> tasks) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.tasks = tasks;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String name;

    @Column(nullable=false, unique=true)
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;





    // Явные сеттеры (на случай, если Lombok не работает)
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
}
