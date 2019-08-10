package com.canx.cebulax.cebulax.service;

import com.canx.cebulax.cebulax.dto.UserAuthenticateDTO;
import com.canx.cebulax.cebulax.dto.UserCreateDTO;
import com.canx.cebulax.cebulax.exception.BadCredentialsException;
import com.canx.cebulax.cebulax.exception.EntityAlreadyExistsException;
import com.canx.cebulax.cebulax.model.ApiToken;
import com.canx.cebulax.cebulax.model.Family;
import com.canx.cebulax.cebulax.model.User;
import com.canx.cebulax.cebulax.repository.UserRepository;
import com.canx.cebulax.cebulax.security.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final FamilyService familyService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, FamilyService familyService, TokenService tokenService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.familyService = familyService;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(UserCreateDTO userCreateDTO) {
        Family family;

        if (userCreateDTO.getCreateFamily()) {
            family = familyService.createFamily(userCreateDTO);
        } else {
            family = familyService.findByName(userCreateDTO.getFamilyName());
        }

        userRepository.findByName(userCreateDTO.getName()).ifPresent(user -> {
            throw new EntityAlreadyExistsException("User ", userCreateDTO.getName());
        });

        User user = new User();
        user.setName(userCreateDTO.getName());
        user.setPassword(passwordEncoder.hashPassword(userCreateDTO.getPassword()));
        user.setFamily(family);
        user.setFamilyOwner(userCreateDTO.getCreateFamily());

        return userRepository.save(user);
    }

    @Override
    public ApiToken authenticate(UserAuthenticateDTO userAuthenticateDTO) {
        Optional<User> user = userRepository.findByName(userAuthenticateDTO.getName());
        if (!user.isPresent() || !passwordEncoder.passwordsEquals(userAuthenticateDTO.getPassword(), user.get().getPassword())) {
            throw new BadCredentialsException();
        }

        return tokenService.create(user.get());
    }

}
