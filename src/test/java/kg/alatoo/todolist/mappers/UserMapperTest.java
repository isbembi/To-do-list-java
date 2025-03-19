package kg.alatoo.todolist.mappers;

import kg.alatoo.todolist.dto.UserDTO;
import kg.alatoo.todolist.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    private UserMapper userMapper;
    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapper();

        user = new User(1L, "Alice", "alice@example.com", null);
        userDTO = new UserDTO(1L, "Alice", "alice@example.com");
    }

    @Test
    void testToDTO() {
        UserDTO mappedDTO = userMapper.toDTO(user);

        assertThat(mappedDTO).isNotNull();
        assertThat(mappedDTO.getId()).isEqualTo(user.getId());
        assertThat(mappedDTO.getName()).isEqualTo(user.getName());
        assertThat(mappedDTO.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void testToEntity() {
        User mappedUser = userMapper.toEntity(userDTO);

        assertThat(mappedUser).isNotNull();
        assertThat(mappedUser.getId()).isEqualTo(userDTO.getId());
        assertThat(mappedUser.getName()).isEqualTo(userDTO.getName());
        assertThat(mappedUser.getEmail()).isEqualTo(userDTO.getEmail());
    }
}
