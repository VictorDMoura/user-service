package academy.devojo.userservice.service;

import academy.devojo.userservice.common.UserUtils;
import academy.devojo.userservice.domain.User;
import academy.devojo.userservice.repository.UserHardCodedRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserHardCodedRepository userHardCodedRepository;
    @InjectMocks
    private UserUtils userUtils;

    private List<User> users;

    @BeforeEach
    void init() {
        users = userUtils.newUsersList();
    }

    @Test
    @DisplayName("findAll() should a list with all users when successful")
    void findAll_ReturnsAllUsers_WhenSuccessful() {
        BDDMockito.when(userHardCodedRepository.findByFirstName(null)).thenReturn(this.users);

        var users = userService.findAll(null);

        Assertions.assertThat(users).hasSameElementsAs(this.users);
    }

    @Test
    @DisplayName("findAll() should returns a list with found user when name is not null")
    void findAll_ReturnsAllUsers_WhenNameIsNotNull() {
        var name = "John";
        var userFound = this.users.stream()
                .filter(user -> user.getFirstName().equals(name))
                .toList();
        BDDMockito.when(userHardCodedRepository.findByFirstName(name)).thenReturn(userFound);

        var users = userService.findAll(name);

        Assertions.assertThat(users).hasSameElementsAs(userFound);
    }

    @Test
    @DisplayName("save() should return a saved user when successful")
    void save_ReturnsSavedUser_WhenSuccessful() {
        var userToSave = userUtils.newUserToSave();
        BDDMockito.when(userHardCodedRepository.save(userToSave)).thenReturn(userToSave);

        var user = userService.save(userToSave);

        Assertions.assertThat(user).isEqualTo(userToSave);
    }

    @Test
    @DisplayName("findById() should return a user when successful")
    void findById_ReturnsUser_WhenSuccessful() {
        var user = users.get(0);
        var id = user.getId();
        BDDMockito.when(userHardCodedRepository.findById(id)).thenReturn(Optional.of(user));

        var userFound = userService.findById(id);

        Assertions.assertThat(userFound).isEqualTo(user);
    }

    @Test
    @DisplayName("deleteById() should delete a user when successful")
    void deleteById_DeletesUser_WhenSuccessful() {
        var user = users.get(0);
        var id = user.getId();
        BDDMockito.when(userHardCodedRepository.findById(id)).thenReturn(Optional.of(user));

        userService.deleteById(id);

        BDDMockito.verify(userHardCodedRepository).deleteById(id);
    }

    @Test
    @DisplayName("deleteById() should throw exception when user is not found")
    void deleteById_ThrowsException_WhenUserIsNotFound() {
        var id = 99L;
        BDDMockito.when(userHardCodedRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThatException().isThrownBy(() -> userService.deleteById(id))
                .isInstanceOf(ResponseStatusException.class)
                .withMessageContaining("User not found to be deleted")
                .withMessageContaining("NOT_FOUND");
    }

    @Test
    @DisplayName("update() should update a user when successful")
    void update_UpdateUser_WhenSuccessful() {
        var userToUpdate = users.get(0);
        userToUpdate.setFirstName("Philipe");
        BDDMockito.when(userHardCodedRepository.findById(userToUpdate.getId())).thenReturn(Optional.of(userToUpdate));

        userService.update(userToUpdate);

        BDDMockito.verify(userHardCodedRepository).update(userToUpdate);
    }

    @Test
    @DisplayName("update() should throw exception when user is not found")
    void update_ThrowsException_WhenUserIsNotFound() {
        var userToUpdate = users.get(0);
        userToUpdate.setFirstName("Philipe");
        BDDMockito.when(userHardCodedRepository.findById(userToUpdate.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException().isThrownBy(() -> userService.update(userToUpdate))
                .isInstanceOf(ResponseStatusException.class)
                .withMessageContaining("User not found to be updated")
                .withMessageContaining("NOT_FOUND");
    }

    @Test
    @DisplayName("findByEmail() should return a user when successful")
    void findByEmail_ReturnsUser_WhenSuccessful() {
        var user = users.get(0);
        var email = user.getEmail();
        BDDMockito.when(userHardCodedRepository.findByEmail(email)).thenReturn(Optional.of(user));

        var userFound = userService.findByEmail(email);

        Assertions.assertThat(userFound).isPresent().get().isEqualTo(user);
    }

    @Test
    @DisplayName("findByEmail() should throw exception when user is not found")
    void findByEmail_ThrowsException_WhenUserIsNotFound() {
        var email = "ego@email.com";
        BDDMockito.when(userHardCodedRepository.findByEmail(email)).thenReturn(Optional.empty());

        Assertions.assertThatException().isThrownBy(() -> userService.findByEmail(email))
                .isInstanceOf(ResponseStatusException.class)
                .withMessageContaining("User not found by email")
                .withMessageContaining("NOT_FOUND");
    }



}