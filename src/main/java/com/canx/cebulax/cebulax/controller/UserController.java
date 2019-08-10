package com.canx.cebulax.cebulax.controller;

import com.canx.cebulax.cebulax.controller.body.ResponseBodyWrapper;
import com.canx.cebulax.cebulax.dto.UserCreateDTO;
import com.canx.cebulax.cebulax.model.User;
import com.canx.cebulax.cebulax.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseBodyWrapper<User> createUser(@Valid @RequestBody UserCreateDTO user) {
        return ResponseBodyWrapper.from(userService.createUser(user));
    }
}
