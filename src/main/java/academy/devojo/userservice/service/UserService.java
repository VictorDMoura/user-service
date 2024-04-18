package academy.devojo.userservice.service;


import academy.devojo.userservice.domain.User;
import academy.devojo.userservice.repository.UserHardCodedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserHardCodedRepository userHardCodedRepository;

    public List<User> findAll(String name) {
        return userHardCodedRepository.findByFirstName(name);
    }

    public User save(User user) {
        return userHardCodedRepository.save(user);
    }

    public Optional<User> findById(Long id) {
        return userHardCodedRepository.findById(id);
    }

    public void deleteById(Long id) {
        var user = userHardCodedRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
                        "User not found to be deleted"));
        userHardCodedRepository.deleteById(user.getId());
    }

    public void update(User userToUpdate) {
        var user = userHardCodedRepository
                .findById(userToUpdate.getId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
                        "User not found to be updated"));
        userToUpdate.setCreatedAt(user.getCreatedAt());
        userHardCodedRepository.update(userToUpdate);
    }

    public Optional<User> findByEmail(String email) {
        var user = userHardCodedRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        NOT_FOUND,
                        "User not found by email"));
        return Optional.of(user);
    }


}
