package com.canx.cebulax.service;

import com.canx.cebulax.dto.GroupCreateDTO;
import com.canx.cebulax.exception.BadCredentialsException;
import com.canx.cebulax.exception.EntityAlreadyExistsException;
import com.canx.cebulax.exception.EntityNotFoundException;
import com.canx.cebulax.exception.InvalidActionException;
import com.canx.cebulax.model.Group;
import com.canx.cebulax.model.User;
import com.canx.cebulax.repository.GroupRepository;
import com.canx.cebulax.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
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
    @Transactional
    public void leaveGroup(Long groupId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User with id ", userId.toString()));

        Group group = groupRepository.findById(groupId).orElseThrow(
                () -> new EntityNotFoundException("Group with id ", userId.toString()));

        group.getUsers().remove(user);
        groupRepository.save(group);
    }

    @Override
    @Transactional
    public void joinGroup(Long groupId, String secret, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User with id ", userId.toString()));

        Group group = groupRepository.findById(groupId).orElseThrow(
                () -> new EntityNotFoundException("Group with id ", groupId.toString()));

        if (!passwordEncoder.matches(secret, group.getSecret())) {
            throw new BadCredentialsException();
        }

        group.getUsers().add(user);
        groupRepository.save(group);
    }

    @Override
    @Transactional
    public void deleteGroup(Long groupId, Long ownerId) {
        Group group = groupRepository.findById(groupId).orElseThrow(
                () -> new EntityNotFoundException("Group with id ", groupId.toString()));

        if (!group.getOwner().getId().equals(ownerId)) {
            throw new InvalidActionException("delete group", "only group owner can delete group");
        }

        groupRepository.delete(group);
    }
}
