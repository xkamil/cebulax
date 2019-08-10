package com.canx.cebulax.cebulax.service;

import com.canx.cebulax.cebulax.dto.UserCreateDTO;
import com.canx.cebulax.cebulax.exception.EntityAlreadyExistsException;
import com.canx.cebulax.cebulax.exception.EntityNotFoundException;
import com.canx.cebulax.cebulax.model.Family;
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
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private FamilyService familyService;
    @Mock
    private TokenService tokenService;
    private UserService userService;

    private static final String FAMILY_NAME = "wrobel";
    private static final Family family = new Family(FAMILY_NAME);
    private static final User user = new User("roman", "asdfasdfs");

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(userRepository, familyService, tokenService);
    }

    @Test
    void testCreateUserAndFamilyNewUserNewFamilyCreatesUserAndFamily() {
        // given
        userRepositorySaveReturnsUser();
        userRepositoryFindByNameReturnsEmpty();
        familyServiceCreateFamilyReturnsFamily();

        UserCreateDTO userCreateDTO = new UserCreateDTO("roman", "pass", FAMILY_NAME, true);

        // when
        User user = userService.createUser(userCreateDTO);

        // then
        assertThat(user.getName()).isEqualTo(user.getName());

        verify(familyService, times(1)).createFamily(any());
    }

    @Test
    void testCreateUserAndFamilyNewUserExistingFamilyCreatesUser() {
        // given
        userRepositorySaveReturnsUser();
        userRepositoryFindByNameReturnsEmpty();
        familyServiceFindByNameReturnsFamily();

        UserCreateDTO userCreateDTO = new UserCreateDTO("roman", "pass", FAMILY_NAME, false);

        // when
        User user = userService.createUser(userCreateDTO);

        // then
        assertThat(user.getName()).isEqualTo(user.getName());

        verify(familyService, times(0)).createFamily(any());
    }

    @Test
    void testCreateUserAndFamilyExistingUserExistingFamilyThrows() {
        // given
        userRepositorySaveReturnsUser();
        userRepositoryFindByNameReturnsUser();
        familyServiceFindByNameReturnsFamily();

        UserCreateDTO userCreateDTO = new UserCreateDTO("roman", "pass", FAMILY_NAME, false);

        // when + then
        assertThrows(EntityAlreadyExistsException.class, () -> userService.createUser(userCreateDTO));
    }

    @Test
    void testCreateUserAndFamilyNewUserNonExistingFamilyThrows() {
        // given
        userRepositorySaveReturnsUser();
        userRepositoryFindByNameReturnsEmpty();
        familyServiceFindByNameThrowsNotFound();

        UserCreateDTO userCreateDTO = new UserCreateDTO("roman", "pass", FAMILY_NAME, false);

        // when + then
        assertThrows(EntityNotFoundException.class, () -> userService.createUser(userCreateDTO));
    }

    @Test
    void testCreateUserAndFamilyNewUserExistingFamilyThrows() {
        // given
        userRepositorySaveReturnsUser();
        userRepositoryFindByNameReturnsEmpty();
        familyServiceCreateFamilyThrowsConflict();

        UserCreateDTO userCreateDTO = new UserCreateDTO("roman", "pass", FAMILY_NAME, true);

        // when + then
        assertThrows(EntityAlreadyExistsException.class, () -> userService.createUser(userCreateDTO));
    }

    private void userRepositorySaveReturnsUser() {
        when(userRepository.save(any())).thenReturn(user);
    }

    private void userRepositoryFindByNameReturnsUser() {
        when(userRepository.findByName(any())).thenReturn(Optional.of(user));
    }

    private void userRepositoryFindByNameReturnsEmpty() {
        when(userRepository.findByName(any())).thenReturn(Optional.empty());
    }

    private void familyServiceCreateFamilyReturnsFamily() {
        when(familyService.createFamily(any())).thenReturn(family);
    }

    private void familyServiceCreateFamilyThrowsConflict() {
        when(familyService.createFamily(any())).thenThrow(EntityAlreadyExistsException.class);
    }

    private void familyServiceFindByNameReturnsFamily() {
        when(familyService.findByName(any())).thenReturn(family);
    }

    private void familyServiceFindByNameThrowsNotFound() {
        when(familyService.findByName(any())).thenThrow(EntityNotFoundException.class);
    }

}
