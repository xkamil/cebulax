package com.canx.cebulax.cebulax.controller;

import com.canx.cebulax.cebulax.controller.body.ResponseBodyWrapper;
import com.canx.cebulax.cebulax.dto.UserAuthenticateDTO;
import com.canx.cebulax.cebulax.dto.UserCreateDTO;
import com.canx.cebulax.cebulax.model.ApiToken;
import com.canx.cebulax.cebulax.model.User;
import com.canx.cebulax.cebulax.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/public/users")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseBodyWrapper<User> createUser(@Valid @RequestBody UserCreateDTO user) {
        return ResponseBodyWrapper.from(userService.createUser(user));
    }

    @PostMapping("/api/public/users/authenticate")
    @ResponseStatus(HttpStatus.OK)
    ResponseBodyWrapper<ApiToken> authenticate(@Valid @RequestBody UserAuthenticateDTO user) {
        return ResponseBodyWrapper.from(userService.authenticate(user));
    }


}
