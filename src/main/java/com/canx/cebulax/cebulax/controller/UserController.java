package com.canx.cebulax.cebulax.controller;

import com.canx.cebulax.cebulax.controller.body.ResponseFactory;
import com.canx.cebulax.cebulax.dto.UserCreateDTO;
import com.canx.cebulax.cebulax.security.CurrentUser;
import com.canx.cebulax.cebulax.security.UserPrincipal;
import com.canx.cebulax.cebulax.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final ResponseFactory responseFactory;

    public UserController(UserService userService, ResponseFactory responseFactory) {
        this.userService = userService;
        this.responseFactory = responseFactory;
    }

    @PostMapping("")
    ResponseEntity<?> createUser(@Valid @RequestBody UserCreateDTO user) {
        return responseFactory.created(userService.createUser(user));
    }


    //TODO remove
    @GetMapping("")
    ResponseEntity<?> getUsers(@CurrentUser UserPrincipal currentUser) {
        return responseFactory.ok(currentUser);
    }


}
