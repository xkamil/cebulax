package com.canx.cebulax.cebulax.service;

import com.canx.cebulax.cebulax.dto.UserAuthenticateDTO;
import com.canx.cebulax.cebulax.dto.UserCreateDTO;
import com.canx.cebulax.cebulax.exception.BadCredentialsException;
import com.canx.cebulax.cebulax.exception.EntityAlreadyExistsException;
import com.canx.cebulax.cebulax.model.ApiToken;
import com.canx.cebulax.cebulax.model.User;
import com.canx.cebulax.cebulax.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, TokenService tokenService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(UserCreateDTO userCreateDTO) {
        userRepository.findByName(userCreateDTO.getName()).ifPresent(user -> {
            throw new EntityAlreadyExistsException("User ", userCreateDTO.getName());
        });
        User user = new User();
        user.setName(userCreateDTO.getName());
        user.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public ApiToken authenticate(UserAuthenticateDTO userAuthenticateDTO) {
        Optional<User> user = userRepository.findByName(userAuthenticateDTO.getName());
        if (!user.isPresent() || !passwordEncoder.matches(userAuthenticateDTO.getPassword(), user.get().getPassword())) {
            throw new BadCredentialsException();
        }

        return tokenService.create(user.get());
    }

}
