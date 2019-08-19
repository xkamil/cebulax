package com.canx.cebulax.service;

import com.canx.cebulax.dto.GroupCreateDTO;
import com.canx.cebulax.exception.EntityAlreadyExistsException;
import com.canx.cebulax.exception.EntityNotFoundException;
import com.canx.cebulax.exception.UnauthorizedException;
import com.canx.cebulax.model.Group;
import com.canx.cebulax.model.User;
import com.canx.cebulax.repository.GroupRepository;
import com.google.common.collect.Sets;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public GroupServiceImpl(GroupRepository groupRepository, UserService userService, PasswordEncoder passwordEncoder) {
        this.groupRepository = groupRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Group createGroup(GroupCreateDTO groupCreateDTO, Long userId) {
        groupRepository.findByName(groupCreateDTO.getName()).ifPresent(f -> {
            throw new EntityAlreadyExistsException("Group with name " + groupCreateDTO.getName());
        });
        User user = userService.findById(userId);
        Set<User> users = Sets.newHashSet(user);
        String hashedSecret = passwordEncoder.encode(groupCreateDTO.getSecret());
        Group group = new Group(groupCreateDTO.getName(), hashedSecret, user, users, Sets.newHashSet());
        return groupRepository.save(group);
    }

    @Override
    public Group findByName(String groupName) {
        return groupRepository.findByName(groupName).orElseThrow(() ->
                new EntityNotFoundException("Group with name " + groupName));
    }

    @Override
    public Group findById(Long id) {
        return groupRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Group with id " + id.toString()));
    }

    @Override
    public List<Group> findAll() {
        return groupRepository.findAll();
    }


    @Override
    public void leaveGroup(Long groupId, Long userId) {
        User user = userService.findById(userId);
        Group group = findById(groupId);
        group.getUsers().remove(user);
        groupRepository.save(group);
    }

    @Override
    public void joinGroup(Long groupId, String secret, Long userId) {
        User user = userService.findById(userId);
        Group group = findById(groupId);
        if (!passwordEncoder.matches(secret, group.getSecret())) {
            throw new UnauthorizedException("Invalid group secret");
        }
        group.getUsers().add(user);
        groupRepository.save(group);
    }

    @Override
    public void deleteGroup(Long groupId, Long ownerId) {
        Group group = findById(groupId);
        if (!group.getOwner().getId().equals(ownerId)) {
            throw new UnauthorizedException("Only group owner can delete group");
        }
        groupRepository.delete(group);
    }
}
