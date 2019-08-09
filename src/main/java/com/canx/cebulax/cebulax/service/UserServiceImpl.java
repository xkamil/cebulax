package com.canx.cebulax.cebulax.service;

import com.canx.cebulax.cebulax.dto.UserDTO;
import com.canx.cebulax.cebulax.exception.UserAlreadyExistsException;
import com.canx.cebulax.cebulax.model.User;
import com.canx.cebulax.cebulax.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public long createUser(UserDTO userDTO) {
        userRepository.findByName(userDTO.getName()).ifPresent(user -> {
            throw new UserAlreadyExistsException(userDTO.getName());
        });

        User user = User.fromDTO(userDTO);
        return userRepository.save(user).getId();
    }

}
