package com.canx.cebulax.cebulax.service;

import com.canx.cebulax.cebulax.dto.UserCreateDTO;
import com.canx.cebulax.cebulax.model.User;

public interface UserService {

    User createUser(UserCreateDTO userCreateDTO);
}
