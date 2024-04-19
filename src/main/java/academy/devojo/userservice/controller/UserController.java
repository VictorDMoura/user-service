package academy.devojo.userservice.controller;


import academy.devojo.userservice.mapper.UserMapper;
import academy.devojo.userservice.request.UserPostRequest;
import academy.devojo.userservice.response.UserGetResponse;
import academy.devojo.userservice.response.UserPostResponse;
import academy.devojo.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"v1/users", "v1/users/"}, produces = MediaType.APPLICATION_JSON_VALUE)
@Log4j2
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper mapper;

    @GetMapping
    public ResponseEntity<List<UserGetResponse>> findAll(@RequestParam(required = false) String name) {
        log.info("Request received to list all users, param name '{}'", name);
        var users = userService.findAll(name);
        List<UserGetResponse> response = mapper.toUserGetResponseList(users);
        return ResponseEntity.ok(response);
    }

    @GetMapping("email")
    public ResponseEntity<UserGetResponse> findByEmail(@RequestParam String email) {
        log.info("Request received to find user by email '{}'", email);
        var user = userService.findByEmail(email).get();
        return ResponseEntity.ok(mapper.toUserGetResponse(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserGetResponse> findById(@PathVariable Long id) {
        log.info("Request received to find user by id '{}'", id);
        var user = userService.findById(id).get();
        return ResponseEntity.ok(mapper.toUserGetResponse(user));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.info("Request received to delete user with id '{}'", id);
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<UserPostResponse> save(@RequestBody UserPostRequest request) {
        log.info("Request received to save user '{}'", request);
        var user = mapper.toUser(request);
        var response  = mapper.toUserPostResponse(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody UserPostRequest request) {
        log.info("Request received to update user '{}'", request);
        var user = mapper.toUser(request);
        userService.update(user);
        return ResponseEntity.noContent().build();
    }


}
