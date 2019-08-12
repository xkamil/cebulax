package com.canx.cebulax.cebulax.service;

import com.canx.cebulax.cebulax.dto.GroupCreateDTO;
import com.canx.cebulax.cebulax.exception.BadCredentialsException;
import com.canx.cebulax.cebulax.exception.EntityAlreadyExistsException;
import com.canx.cebulax.cebulax.exception.EntityNotFoundException;
import com.canx.cebulax.cebulax.model.Group;
import com.canx.cebulax.cebulax.model.User;
import com.canx.cebulax.cebulax.repository.GroupRepository;
import com.canx.cebulax.cebulax.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
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
    public Group createGroup(GroupCreateDTO groupCreateDTO, Long userId) {
        groupRepository.findByName(groupCreateDTO.getName()).ifPresent(f -> {
            throw new EntityAlreadyExistsException("Group with name", groupCreateDTO.getName());
        });

        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User with id ", userId.toString()));

        Set<User> users = new HashSet<>();
        users.add(user);

        String hashedSecret = passwordEncoder.encode(groupCreateDTO.getSecret());
        Group group = new Group(groupCreateDTO.getName(), hashedSecret, user, users);
        return groupRepository.save(group);
    }

    @Override
    public Group findByName(String groupName) {
        return groupRepository.findByName(groupName).orElseThrow(() ->
                new EntityNotFoundException("Group with name", groupName));
    }

    @Override
    public List<Group> findAll() {
        return groupRepository.findAll();
    }


    @Override
    public void leaveGroup(Long groupId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User with id ", userId.toString()));

        Group group = groupRepository.findById(groupId).orElseThrow(
                () -> new EntityNotFoundException("Group with id ", userId.toString()));

        group.getUsers().remove(user);
        groupRepository.save(group);
    }

    @Override
    public void joinGroup(Long groupId, String secret, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User with id ", userId.toString()));

        Group group = groupRepository.findById(groupId).orElseThrow(
                () -> new EntityNotFoundException("Group with id ", userId.toString()));

        if (!passwordEncoder.matches(secret, group.getSecret())) {
            throw new BadCredentialsException();
        }

        group.getUsers().add(user);
        groupRepository.save(group);
    }
}
