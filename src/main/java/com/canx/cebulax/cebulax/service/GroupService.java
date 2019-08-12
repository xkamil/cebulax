package com.canx.cebulax.cebulax.service;

import com.canx.cebulax.cebulax.dto.GroupCreateDTO;
import com.canx.cebulax.cebulax.model.Group;

import java.util.List;

public interface GroupService {

    Group createGroup(GroupCreateDTO groupCreateDTO, Long userId);

    Group findByName(String groupName);

    List<Group> findAll();

    void joinGroup(Long groupId, String secret, Long userId);

    void leaveGroup(Long groupId, Long userId);

    void deleteGroup(Long groupId, Long ownerId);
}
