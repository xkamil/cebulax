package com.canx.cebulax.cebulax.controller;

import com.canx.cebulax.cebulax.controller.body.ResponseFactory;
import com.canx.cebulax.cebulax.dto.UserAuthenticateDTO;
import com.canx.cebulax.cebulax.dto.UserCreateDTO;
import com.canx.cebulax.cebulax.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final UserService userService;
    private final ResponseFactory responseFactory;

    public AuthController(UserService userService, ResponseFactory responseFactory) {
        this.userService = userService;
        this.responseFactory = responseFactory;
    }

    @PostMapping("/authenticate")
    ResponseEntity<?> authenticate(@Valid @RequestBody UserAuthenticateDTO user) {
        return responseFactory.ok(userService.authenticate(user));
    }

    @PostMapping("/register")
    ResponseEntity<?> register(@Valid @RequestBody UserCreateDTO user) {
        return responseFactory.created(userService.createUser(user));
    }

}
