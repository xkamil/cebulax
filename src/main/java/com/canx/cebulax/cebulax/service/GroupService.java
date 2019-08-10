package com.canx.cebulax.cebulax.service;

import com.canx.cebulax.cebulax.dto.GroupCreateDTO;
import com.canx.cebulax.cebulax.model.Group;

public interface GroupService {

    Group createGroup(GroupCreateDTO groupCreateDTO);

    Group findByName(String groupName);
}
