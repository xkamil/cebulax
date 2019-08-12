package com.canx.cebulax.cebulax.service;

import com.canx.cebulax.cebulax.dto.GroupCreateDTO;
import com.canx.cebulax.cebulax.exception.EntityAlreadyExistsException;
import com.canx.cebulax.cebulax.exception.EntityNotFoundException;
import com.canx.cebulax.cebulax.model.Group;
import com.canx.cebulax.cebulax.model.User;
import com.canx.cebulax.cebulax.repository.GroupRepository;
import com.canx.cebulax.cebulax.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class GroupServiceTest {

//    private User user;
//    private Group group;
//
//    private PasswordEncoder passwordEncoder;
//
//    @Mock
//    private GroupRepository groupRepository;
//    @Mock
//    private UserRepository userRepository;
//    private GroupService groupService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.initMocks(this);
//        passwordEncoder = new BCryptPasswordEncoder();
//        groupService = new GroupServiceImpl(groupRepository, userRepository, passwordEncoder);
//        user = new User("roman", passwordEncoder.encode("pass"));
//        group = new Group("wrobel", passwordEncoder.encode("passs"), user, new HashSet<User>() {{
//            add(user);
//        }});
//    }
//
//    @Test
//    void testCreateGroupNonExistingName() {
//        // given
//        groupRepositorySaveReturnsGroup();
//        groupRepositoryFindByNameReturnsEmpty();
//        userRepositoryFindByIdReturnsUser();
//
//        GroupCreateDTO groupCreateDTO = new GroupCreateDTO("wrobel", "pass", 1L);
//
//        // when
//        Group group = groupService.createGroup(groupCreateDTO);
//
//        // then
//        assertThat(group.getName()).isEqualTo(this.group.getName());
//    }
//
//    @Test
//    void testCreateGroupWithExistingName() {
//        // given
//        groupRepositoryFindByNameReturnsGroup();
//        userRepositoryFindByIdReturnsUser();
//
//        GroupCreateDTO groupCreateDTO = new GroupCreateDTO("wrobel", "pass", 1L);
//
//        // when + then
//        assertThrows(EntityAlreadyExistsException.class, () -> groupService.createGroup(groupCreateDTO));
//    }
//
//    @Test
//    void testCreateNonExistingUser() {
//        // given
//        groupRepositoryFindByNameReturnsEmpty();
//        userRepositoryFindByIdReturnsEmpty();
//
//        GroupCreateDTO groupCreateDTO = new GroupCreateDTO("wrobel", "pass", 1L);
//
//        // when + then
//        assertThrows(EntityNotFoundException.class, () -> groupService.createGroup(groupCreateDTO));
//    }
//
//    private void groupRepositorySaveReturnsGroup() {
//        when(groupRepository.save(any())).thenReturn(group);
//    }
//
//    private void groupRepositoryFindByNameReturnsGroup() {
//        when(groupRepository.findByName(any())).thenReturn(Optional.of(group));
//    }
//
//    private void groupRepositoryFindByNameReturnsEmpty() {
//        when(groupRepository.findByName(any())).thenReturn(Optional.empty());
//    }
//
//    private void userRepositoryFindByIdReturnsUser() {
//        when(userRepository.findById(any())).thenReturn(Optional.of(user));
//    }
//
//    private void userRepositoryFindByIdReturnsEmpty() {
//        when(userRepository.findById(any())).thenReturn(Optional.empty());
//    }
}
