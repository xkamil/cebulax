package com.canx.cebulax.cebulax.service;

import com.canx.cebulax.cebulax.dto.UserAuthenticateDTO;
import com.canx.cebulax.cebulax.dto.UserCreateDTO;
import com.canx.cebulax.cebulax.exception.BadCredentialsException;
import com.canx.cebulax.cebulax.exception.EntityAlreadyExistsException;
import com.canx.cebulax.cebulax.model.ApiToken;
import com.canx.cebulax.cebulax.model.Family;
import com.canx.cebulax.cebulax.model.User;
import com.canx.cebulax.cebulax.repository.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final FamilyService familyService;
    private final TokenService tokenService;

    public UserServiceImpl(UserRepository userRepository, FamilyService familyService, TokenService tokenService) {
        this.userRepository = userRepository;
        this.familyService = familyService;
        this.tokenService = tokenService;
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
        user.setPassword(hashPassword(userCreateDTO.getPassword()));
        user.setFamily(family);
        user.setFamilyOwner(userCreateDTO.getCreateFamily());

        return userRepository.save(user);
    }

    @Override
    public ApiToken authenticate(UserAuthenticateDTO userAuthenticateDTO) {
        Optional<User> user = userRepository.findByName(userAuthenticateDTO.getName());
        user.orElseThrow(BadCredentialsException::new);

        return tokenService.create(user.get());
    }

    private String hashPassword(String password) {
        return new String(DigestUtils.sha256(password));
    }

    private boolean passwordsEquals(String plain, String hashed) {
        return hashPassword(plain).equals(hashed);
    }

}
