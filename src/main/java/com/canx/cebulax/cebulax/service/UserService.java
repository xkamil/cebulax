package com.canx.cebulax.cebulax.service;

import com.canx.cebulax.cebulax.dto.UserAuthenticateDTO;
import com.canx.cebulax.cebulax.dto.UserCreateDTO;
import com.canx.cebulax.cebulax.model.ApiToken;
import com.canx.cebulax.cebulax.model.User;

public interface UserService {

    User createUser(UserCreateDTO userCreateDTO);

    ApiToken authenticate(UserAuthenticateDTO userAuthenticateDTO);
}
