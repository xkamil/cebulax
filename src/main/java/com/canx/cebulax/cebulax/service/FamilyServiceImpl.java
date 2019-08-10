package com.canx.cebulax.cebulax.service;

import com.canx.cebulax.cebulax.dto.FamilyCreateDTO;
import com.canx.cebulax.cebulax.exception.EntityAlreadyExistsException;
import com.canx.cebulax.cebulax.exception.EntityNotFoundException;
import com.canx.cebulax.cebulax.model.Family;
import com.canx.cebulax.cebulax.model.User;
import com.canx.cebulax.cebulax.repository.FamilyRepository;
import com.canx.cebulax.cebulax.repository.UserRepository;
import com.canx.cebulax.cebulax.security.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class FamilyServiceImpl implements FamilyService {

    private final FamilyRepository familyRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public FamilyServiceImpl(FamilyRepository familyRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.familyRepository = familyRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Family createFamily(FamilyCreateDTO familyCreateDTO) {
        familyRepository.findByName(familyCreateDTO.getName()).ifPresent(f -> {
            throw new EntityAlreadyExistsException("Family", familyCreateDTO.getName());
        });

        User user = userRepository.findById(familyCreateDTO.getCreatedBy()).orElseThrow(
                () -> new EntityNotFoundException("User with id ", familyCreateDTO.getCreatedBy().toString()));

        Set<User> users = new HashSet<>();
        users.add(user);

        String hashedSecret = passwordEncoder.hashPassword(familyCreateDTO.getSecret());
        Family family = new Family(familyCreateDTO.getName(), hashedSecret, user, users);
        return familyRepository.save(family);
    }

    @Override
    public Family findByName(String familyName) {
        return familyRepository.findByName(familyName).orElseThrow(() ->
                new EntityNotFoundException("Family ", familyName));
    }
}
