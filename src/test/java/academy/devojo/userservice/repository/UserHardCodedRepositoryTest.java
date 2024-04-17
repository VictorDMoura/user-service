package academy.devojo.userservice.repository;


import academy.devojo.userservice.common.UserUtils;
import academy.devojo.userservice.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class UserHardCodedRepositoryTest {

    @InjectMocks
    private UserHardCodedRepository repository;

    @Mock
    private UserData userData;

    private List<User> users;

    @InjectMocks
    private UserUtils userUtils;

    @BeforeEach
    void init() {
        users = userUtils.newUsersList();
        BDDMockito.when(userData.getUsers()).thenReturn(users);
    }


    @Test
    @DisplayName("findAll() should return a list with all users when successful")
    void findAll_ReturnsAllUsers_WhenSuccessful() {
        var users = repository.findAll();
        Assertions.assertThat(users).hasSameElementsAs(this.users);
    }

    @Test
    @DisplayName("findById() returns an object with given id")
    void findById_ReturnsAUser_WhenSuccessful() {
        var userOptional = repository.findById(3L);
        Assertions.assertThat(userOptional).contains(users.get(2));
    }

    @Test
    @DisplayName("findByFirstName() returns all users when first name is null")
    void findByFirstName_ReturnsAllUsers_WhenFirstNameIsNull() {
        var users = repository.findByFirstName(null);
        Assertions.assertThat(users).hasSameElementsAs(this.users);
    }

    @Test
    @DisplayName("findByFirstName() returns a specific user with given first name")
    void findByFirstName_ReturnsAllUsersWithGivenFirstName_WhenFirstNameIsNotNull() {
        var user = repository.findByFirstName("John");
        Assertions.assertThat(user).hasSize(1);
        Assertions.assertThat(user.get(0)).isEqualTo(users.get(0));
    }

    @Test
    @DisplayName("findByEmail() returns an object with given email")
    void findByEmail_ReturnsAUser_WhenSuccessful() {
        var userOptional = repository.findByEmail("johndoe@email.com");
        Assertions.assertThat(userOptional).contains(users.get(0));
    }

    @Test
    @DisplayName("findByEmail() returns empty optional when user is not found")
    void findByEmail_ReturnsEmptyOptional_WhenUserIsNotFound() {
        var userOptional = repository.findByEmail("anything@email.com");
        Assertions.assertThat(userOptional).isEmpty();
    }

    @Test
    @DisplayName("save() should return a user when successful")
    void save_ReturnsUser_WhenSuccessful() {
        var userToSave = userUtils.newUserToSave();
        var user = repository.save(userToSave);
        Assertions.assertThat(user).isEqualTo(userToSave);
    }

    @Test
    @DisplayName("deleteById() should remove a user when successful")
    void deleteById_RemovesUser_WhenSuccessful() {
        var userDeleted = users.get(0);
        repository.deleteById(userDeleted.getId());
        Assertions.assertThat(repository.findAll()).doesNotContain(userDeleted);
    }

    @Test
    @DisplayName("update() should update a user when successful")
    void update_UpdatesUser_WhenSuccessful() {
        var userToUpdate = userUtils.newUserToSave();
        repository.update(userToUpdate);
        Assertions.assertThat(repository.findAll()).contains(userToUpdate);
    }

}