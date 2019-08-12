package com.canx.cebulax.cebulax.service;

import com.canx.cebulax.cebulax.dto.UserAuthenticateDTO;
import com.canx.cebulax.cebulax.dto.UserCreateDTO;
import com.canx.cebulax.cebulax.model.JwtAuthenticationResponse;
import com.canx.cebulax.cebulax.model.User;

public interface UserService {

    User createUser(UserCreateDTO userCreateDTO);

    JwtAuthenticationResponse authenticate(UserAuthenticateDTO userAuthenticateDTO);

    User findById(Long id);
}
