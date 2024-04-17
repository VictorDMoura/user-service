package academy.devojo.userservice.repository;

import academy.devojo.userservice.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserHardCodedRepository {

    private final UserData userData;

    public List<User> findAll() {
        return userData.getUsers();
    }

    public Optional<User> findById(Long id) {
        return userData.getUsers()
                .stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    public List<User> findByFirstName(String firstName) {
        return firstName == null ? findAll() :
                userData.getUsers()
                        .stream()
                        .filter(user -> user.getFirstName().equalsIgnoreCase(firstName))
                        .toList();
    }

    public Optional<User> findByEmail(String email) {
        return userData.getUsers()
                .stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public User save(User user) {
        userData.getUsers().add(user);
        return user;
    }

    public void deleteById(Long id) {
        userData.getUsers().removeIf(user -> user.getId().equals(id));
    }

    public void update(User user) {
        deleteById(user.getId());
        save(user);
    }
}
