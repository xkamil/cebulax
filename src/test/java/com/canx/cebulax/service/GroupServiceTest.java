package com.canx.cebulax.service;

import com.canx.cebulax.dto.GroupCreateDTO;
import com.canx.cebulax.dto.UserCreateDTO;
import com.canx.cebulax.exception.BadCredentialsException;
import com.canx.cebulax.exception.EntityAlreadyExistsException;
import com.canx.cebulax.exception.EntityNotFoundException;
import com.canx.cebulax.exception.InvalidActionException;
import com.canx.cebulax.model.Group;
import com.canx.cebulax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class GroupServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    GroupService sut;

    User user1;
    User user2;
    String groupName;

    @BeforeEach
    void setUpGroupServiceTest() {
        groupName = UUID.randomUUID().toString().substring(0, 10);

        if (user1 == null && user2 == null) {
            UserCreateDTO userInput1 = new UserCreateDTO(UUID.randomUUID().toString().substring(0, 10), "pass1");
            UserCreateDTO userInput2 = new UserCreateDTO(UUID.randomUUID().toString().substring(0, 10), "pass1");
            user1 = userService.createUser(userInput1);
            user2 = userService.createUser(userInput2);
        }
    }

    @Test
    void testCreateGroupNonExistingName() {
        // given
        GroupCreateDTO groupCreateDTO = new GroupCreateDTO(groupName, "gpass1");

        // when
        Group group = sut.createGroup(groupCreateDTO, user1.getId());

        // then
        assertThat(group.getId()).isNotNull();
        assertThat(group.getName()).isEqualTo(groupName);
        assertThat(group.getSecret()).isNotEmpty();
        assertThat(group.getOwner().getId()).isEqualTo(user1.getId());
        assertThat(group.getUsers()).hasSize(1);
        assertThat(group.getUsers()).contains(user1);
    }

    @Test
    void testCreateGroupExistingName() {
        // given
        GroupCreateDTO groupCreateDTO = new GroupCreateDTO(groupName, "gpass1");
        sut.createGroup(groupCreateDTO, user1.getId());

        // when + then
        assertThrows(EntityAlreadyExistsException.class, () -> sut.createGroup(groupCreateDTO, user1.getId()));
    }

    @Test
    void testFindByNameExistingName() {
        // given
        GroupCreateDTO groupCreateDTO = new GroupCreateDTO(groupName, "gpass1");
        Group createdGroup = sut.createGroup(groupCreateDTO, user1.getId());

        // when
        Group foundGroup = sut.findByName(groupName);

        // then
        assertThat(foundGroup).isEqualTo(createdGroup);
    }

    @Test
    void testFindByNameNonExistingName() {
        // when + then
        assertThrows(EntityNotFoundException.class, () -> sut.findByName(groupName));
    }

    @Test
    void testJoinGroupExistingGroupAndValidSecret() {
        // given
        GroupCreateDTO groupCreateDTO = new GroupCreateDTO(groupName, "gpass1");
        Group group = sut.createGroup(groupCreateDTO, user1.getId());

        // when
        sut.joinGroup(group.getId(), "gpass1", user2.getId());

        // then
        Group updatedGroup = sut.findByName(groupName);
        assertThat(updatedGroup.getUsers()).hasSize(2);
        assertThat(updatedGroup.getUsers()).contains(user2);
    }

    @Test
    void testJoinGroupExistingGroupAndInvalidSecret() {
        // given
        GroupCreateDTO groupCreateDTO = new GroupCreateDTO(groupName, "gpass1");
        Group group = sut.createGroup(groupCreateDTO, user1.getId());

        // when + then
        assertThrows(BadCredentialsException.class, () -> sut.joinGroup(group.getId(), "pass1", user2.getId()));
    }

    @Test
    void testJoinGroupNonExistingGroup() {
        // when + then
        assertThrows(EntityNotFoundException.class, () -> sut.joinGroup(Long.MAX_VALUE, "pass1", user2.getId()));
    }

    @Test
    void testLeaveGroupExistingGroupUserMember() {
        // given
        GroupCreateDTO groupCreateDTO = new GroupCreateDTO(groupName, "gpass1");
        Group group = sut.createGroup(groupCreateDTO, user1.getId());

        // when
        sut.leaveGroup(group.getId(), user1.getId());

        // then
        Group updatedGroup = sut.findByName(group.getName());
        assertThat(updatedGroup.getUsers()).hasSize(0);
    }

    @Test
    void testDeleteGroupExistingGroupOwnerId() {
        // given
        GroupCreateDTO groupCreateDTO = new GroupCreateDTO(groupName, "gpass1");
        Group group = sut.createGroup(groupCreateDTO, user1.getId());

        // when
        sut.deleteGroup(group.getId(), user1.getId());

        // then
        assertThrows(EntityNotFoundException.class, () -> sut.findByName(group.getName()));
    }

    @Test
    void testDeleteGroupExistingGroupNotOwnerId() {
        // given
        GroupCreateDTO groupCreateDTO = new GroupCreateDTO(groupName, "gpass1");
        Group group = sut.createGroup(groupCreateDTO, user1.getId());

        // when + then
        assertThrows(InvalidActionException.class, () -> sut.deleteGroup(group.getId(), user2.getId()));
        Group existingGroup = sut.findByName(group.getName());
        assertThat(existingGroup).isEqualTo(group);
    }
}
