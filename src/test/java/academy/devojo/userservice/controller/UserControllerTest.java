package academy.devojo.userservice.controller;


import academy.devojo.userservice.common.FileUtils;
import academy.devojo.userservice.common.UserUtils;
import academy.devojo.userservice.repository.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    private UserData userData;

    @Autowired
    private FileUtils fileUtils;
    @Autowired
    private UserUtils userUtils;

    private static final String URL = "/v1/users";

    @BeforeEach
    void setUp() {
        BDDMockito.when(userData.getUsers()).thenReturn(userUtils.newUsersList());
    }

    @Test
    @DisplayName("findAll() should a list with all users when successful")
    void findAll_ReturnsAllUsers_WhenSuccessful() throws Exception {
        var response = fileUtils.readResourceFile("user/get-user-null-name-200.json");
        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("findByEmail() should return a user when successful")
    void findByEmail_ReturnsUser_WhenSuccessful() throws Exception {
        var response = fileUtils.readResourceFile("user/get-user-email-200.json");
        var validEmail = "johndoe@email.com";
        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/email")
                        .param("email", validEmail))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("findAll() returns a list and when find user status code 200 when successful")
    void findById_ReturnsStatusCode200_WhenSuccessful() throws Exception {
        var response = fileUtils.readResourceFile("user/get-user-Jane-name-200.json");
        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("name", "Jane"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("deleteById() should delete a user when successful")
    void deleteById_DeletesUser_WhenSuccessful() throws Exception {
        var validId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/{id}", validId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("save() should save a user when successful")
    void save_SavesUser_WhenSuccessful() throws Exception {
        var request = fileUtils.readResourceFile("user/post-request-user-200.json");
        var response = fileUtils.readResourceFile("user/post-response-user-201.json");
        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .contentType("application/json")
                        .content(request))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("update() should update a user when successful")
    void update_UpdatesUser_WhenSuccessful() throws Exception {
        var request = fileUtils.readResourceFile("user/put-request-user-200.json");
        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                        .contentType("application/json")
                        .content(request))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("findAll() should return a empty list when name not found and status code 200")
    void findAll_ReturnsEmptyList_WhenNameNotFound() throws Exception {
        var response = fileUtils.readResourceFile("user/get-user-not-found-name-200.json");
        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("name", "NotFound"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("findByEmail() should return a exception when user not found")
    void findByEmail_ReturnsException_WhenUserNotFound() throws Exception {
        var invalidEmail = "invalidemail@email.com";
        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/email")
                        .param("email", invalidEmail))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("findById() should return a exception when user not found")
    void findById_ReturnsException_WhenUserNotFound() throws Exception {
        var invalidId = 99L;
        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", invalidId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("User not found by id: " + invalidId));
    }

    @Test
    @DisplayName("deleteById() should throw exception when user is not found")
    void deleteById_ThrowsException_WhenUserIsNotFound() throws Exception {
        var invalidId = 99L;
        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/{id}", invalidId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("User not found to be deleted"));
    }


}