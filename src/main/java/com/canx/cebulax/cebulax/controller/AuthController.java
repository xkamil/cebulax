package com.canx.cebulax.cebulax.controller;

import com.canx.cebulax.cebulax.controller.body.ResponseBodyWrapper;
import com.canx.cebulax.cebulax.dto.UserAuthenticateDTO;
import com.canx.cebulax.cebulax.model.ApiToken;
import com.canx.cebulax.cebulax.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/auth")
    @ResponseStatus(HttpStatus.OK)
    ResponseBodyWrapper<ApiToken> authenticate(@Valid @RequestBody UserAuthenticateDTO user) {
        return ResponseBodyWrapper.from(userService.authenticate(user));
    }


}
