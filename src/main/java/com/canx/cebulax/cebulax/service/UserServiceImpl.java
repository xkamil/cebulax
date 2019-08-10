package com.canx.cebulax.cebulax.service;

import com.canx.cebulax.cebulax.dto.UserCreateDTO;
import com.canx.cebulax.cebulax.exception.EntityAlreadyExistsException;
import com.canx.cebulax.cebulax.model.Family;
import com.canx.cebulax.cebulax.model.User;
import com.canx.cebulax.cebulax.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final FamilyService familyService;

    public UserServiceImpl(UserRepository userRepository, FamilyService familyService) {
        this.userRepository = userRepository;

        this.familyService = familyService;
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

        User user = User.fromDTO(userCreateDTO);
        user.setFamily(family);
        user.setFamilyOwner(userCreateDTO.getCreateFamily());
        return userRepository.save(user);
    }

}
