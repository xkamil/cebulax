package com.canx.cebulax.service;

import com.canx.cebulax.dto.UserAuthenticateDTO;
import com.canx.cebulax.dto.UserCreateDTO;
import com.canx.cebulax.model.JwtAuthenticationResponse;
import com.canx.cebulax.model.User;

public interface UserService {

    User createUser(UserCreateDTO userCreateDTO);

    JwtAuthenticationResponse authenticate(UserAuthenticateDTO userAuthenticateDTO);

    User findById(Long id);
}
