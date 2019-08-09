package com.canx.cebulax.cebulax.service;

import com.canx.cebulax.cebulax.dto.UserDTO;
import com.canx.cebulax.cebulax.exception.UserAlreadyExistsException;
import com.canx.cebulax.cebulax.model.User;
import com.canx.cebulax.cebulax.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void testCreateUserValidUser() {
        // given
        userRepositorySaveReturnsUser();
        userRepositoryFindByNameReturnsEmpty();
        UserDTO userDTO = new UserDTO("roman", "pass123");

        // when
        long userId = userService.createUser(userDTO);

        // then
        assertThat(userId).isEqualTo(1L);
    }

    @Test
    void testCreateUserExistingName() {
        // given
        userRepositoryFindByNameReturnsUser();
        UserDTO userDTO = new UserDTO("roman", "pass123");

        // when + then
        assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(userDTO));
    }

    private void userRepositorySaveReturnsUser() {
        when(userRepository.save(any())).thenReturn(new User(1L, "roman", "asdfasdfs"));
    }

    private void userRepositoryFindByNameReturnsUser() {
        when(userRepository.findByName(any())).thenReturn(Optional.of(new User(1L, "roman", "asdfasdfs")));
    }

    private void userRepositoryFindByNameReturnsEmpty() {
        when(userRepository.findByName(any())).thenReturn(Optional.empty());
    }
}
