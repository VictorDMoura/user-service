package academy.devojo.userservice.repository;

import academy.devojo.userservice.domain.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserData {

    private final List<User> users = new ArrayList<>();

    {
        var user1 = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@emai.com")
                .createdAt(LocalDateTime.now())
                .build();
        var user2 = User.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Doe")
                .email("janedoe@email.com")
                .createdAt(LocalDateTime.now())
                .build();

        var user3 = User.builder()
                .id(3L)
                .firstName("Alice")
                .lastName("Doe")
                .email("alicedoe@email.com")
                .createdAt(LocalDateTime.now())
                .build();
        users.addAll(List.of(user1, user2, user3));
    }

    public List<User> getUsers() {
        return users;
    }

}
