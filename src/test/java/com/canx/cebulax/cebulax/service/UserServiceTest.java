package com.canx.cebulax.cebulax.service;

import com.canx.cebulax.cebulax.dto.UserAuthenticateDTO;
import com.canx.cebulax.cebulax.dto.UserCreateDTO;
import com.canx.cebulax.cebulax.exception.BadCredentialsException;
import com.canx.cebulax.cebulax.exception.EntityAlreadyExistsException;
import com.canx.cebulax.cebulax.exception.EntityNotFoundException;
import com.canx.cebulax.cebulax.model.ApiToken;
import com.canx.cebulax.cebulax.model.Family;
import com.canx.cebulax.cebulax.model.User;
import com.canx.cebulax.cebulax.repository.UserRepository;
import com.canx.cebulax.cebulax.security.PasswordEncoder;
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

    private PasswordEncoder passwordEncoder;

    private UserService userService;

    private static final String FAMILY_NAME = "wrobel";
    private static final Family family = new Family(FAMILY_NAME);
    private User user = new User("roman", "asdfasdfs");
    private ApiToken token;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        passwordEncoder = new PasswordEncoder();
        user = new User("roman", passwordEncoder.hashPassword("pass"));
        token = new ApiToken(user);
        userService = new UserServiceImpl(userRepository, familyService, tokenService, passwordEncoder);
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

    @Test
    void testAuthenticateValidCredentialsReturnsToken() {
        // given
        userRepositoryFindByNameReturnsUser();
        tokenServiceCreateReturnsToken();

        UserAuthenticateDTO userAuthenticateDTO = new UserAuthenticateDTO("roman", "pass");

        // when
        ApiToken apiToken = userService.authenticate(userAuthenticateDTO);

        // then
        assertThat(apiToken).isNotNull();
    }

    @Test
    void testAuthenticateValidCredentialsReturnsError() {
        // given
        userRepositoryFindByNameReturnsUser();
        tokenServiceCreateReturnsToken();

        UserAuthenticateDTO userAuthenticateDTO = new UserAuthenticateDTO("roman", "pass1");

        // when + then
        assertThrows(BadCredentialsException.class, () -> userService.authenticate(userAuthenticateDTO));
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

    private void tokenServiceCreateReturnsToken() {
        when(tokenService.create(any())).thenReturn(token);
    }

}
