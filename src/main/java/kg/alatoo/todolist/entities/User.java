package kg.alatoo.todolist.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

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


    @NotBlank(message="The name cannot be empty ")
    @Size(min=2, max=50, message = "Name should consist from 2 to 50 chars ")
    @Column(nullable=false, unique=true)
    private String name;

    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Некорректный email")
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

    public void setId(Long id) {
        this.id = id;
    }
}
