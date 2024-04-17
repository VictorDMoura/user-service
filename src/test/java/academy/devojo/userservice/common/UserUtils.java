package academy.devojo.userservice.common;

import academy.devojo.userservice.domain.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserUtils {

    public List<User> newUsersList() {
        var user1 = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@email.com")
                .build();
        var user2 = User.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Doe")
                .email("janedoe@email.com")
                .build();
        var user3 = User.builder()
                .id(3L)
                .firstName("Alice")
                .lastName("Doe")
                .email("alicedoe@email.com")
                .build();

        return new ArrayList<>(List.of(user1, user2, user3));
    }

    public User newUserToSave() {
        return User.builder()
                .id(99L)
                .firstName("Bob")
                .lastName("Doe")
                .email("bobdoe@email.com")
                .build();
    }
}
