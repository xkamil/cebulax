package com.canx.cebulax.cebulax.service;

import com.canx.cebulax.cebulax.dto.GroupCreateDTO;
import com.canx.cebulax.cebulax.exception.EntityAlreadyExistsException;
import com.canx.cebulax.cebulax.exception.EntityNotFoundException;
import com.canx.cebulax.cebulax.model.Group;
import com.canx.cebulax.cebulax.model.User;
import com.canx.cebulax.cebulax.repository.GroupRepository;
import com.canx.cebulax.cebulax.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public GroupServiceImpl(GroupRepository groupRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Group createGroup(GroupCreateDTO groupCreateDTO) {
        groupRepository.findByName(groupCreateDTO.getName()).ifPresent(f -> {
            throw new EntityAlreadyExistsException("Group", groupCreateDTO.getName());
        });

        User user = userRepository.findById(groupCreateDTO.getCreatedBy()).orElseThrow(
                () -> new EntityNotFoundException("User with id ", groupCreateDTO.getCreatedBy().toString()));

        Set<User> users = new HashSet<>();
        users.add(user);

        String hashedSecret = passwordEncoder.encode(groupCreateDTO.getSecret());
        Group group = new Group(groupCreateDTO.getName(), hashedSecret, user, users);
        return groupRepository.save(group);
    }

    @Override
    public Group findByName(String groupName) {
        return groupRepository.findByName(groupName).orElseThrow(() ->
                new EntityNotFoundException("Group ", groupName));
    }
}
