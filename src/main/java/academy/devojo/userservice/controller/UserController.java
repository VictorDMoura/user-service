package academy.devojo.userservice.controller;


import academy.devojo.userservice.mapper.UserMapper;
import academy.devojo.userservice.response.UserGetResponse;
import academy.devojo.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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


}
