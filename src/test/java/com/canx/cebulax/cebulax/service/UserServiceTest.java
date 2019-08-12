package com.canx.cebulax.cebulax.service;

import com.canx.cebulax.cebulax.dto.UserAuthenticateDTO;
import com.canx.cebulax.cebulax.dto.UserCreateDTO;
import com.canx.cebulax.cebulax.exception.BadCredentialsException;
import com.canx.cebulax.cebulax.exception.EntityAlreadyExistsException;
import com.canx.cebulax.cebulax.model.ApiToken;
import com.canx.cebulax.cebulax.model.User;
import com.canx.cebulax.cebulax.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserServiceTest {

//    @Mock
//    private UserRepository userRepository;
//    @Mock
//    private TokenService tokenService;
//    private PasswordEncoder passwordEncoder;
//    private UserService userService;
//
//    private User user;
//    private ApiToken token;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.initMocks(this);
//        passwordEncoder = new BCryptPasswordEncoder();
//        user = new User("roman", passwordEncoder.encode("pass"));
//        token = new ApiToken(user);
//        userService = new UserServiceImpl(userRepository, tokenService, passwordEncoder, authenticationManager, tokenProvider);
//    }
//
//    @Test
//    void testCreateUserNewUser() {
//        // given
//        userRepositorySaveReturnsUser();
//        userRepositoryFindByNameReturnsEmpty();
//
//        UserCreateDTO userCreateDTO = new UserCreateDTO("roman", "pass");
//
//        // when
//        User user = userService.createUser(userCreateDTO);
//
//        // then
//        assertThat(user.getName()).isEqualTo(user.getName());
//    }
//
//    @Test
//    void testCreateUserExistingUser() {
//        // given
//        userRepositorySaveReturnsUser();
//        userRepositoryFindByNameReturnsUser();
//
//        UserCreateDTO userCreateDTO = new UserCreateDTO("roman", "pass");
//
//        // when + then
//        assertThrows(EntityAlreadyExistsException.class, () -> userService.createUser(userCreateDTO));
//    }
//
//    @Test
//    void testAuthenticateValidCredentialsReturnsToken() {
//        // given
//        userRepositoryFindByNameReturnsUser();
//        tokenServiceCreateReturnsToken();
//
//        UserAuthenticateDTO userAuthenticateDTO = new UserAuthenticateDTO("roman", "pass");
//
//        // when
//        ApiToken apiToken = userService.authenticate(userAuthenticateDTO);
//
//        // then
//        assertThat(apiToken).isNotNull();
//    }
//
//    @Test
//    void testAuthenticateValidCredentialsReturnsError() {
//        // given
//        userRepositoryFindByNameReturnsUser();
//        tokenServiceCreateReturnsToken();
//
//        UserAuthenticateDTO userAuthenticateDTO = new UserAuthenticateDTO("roman", "pass1");
//
//        // when + then
//        assertThrows(BadCredentialsException.class, () -> userService.authenticate(userAuthenticateDTO));
//    }
//
//    private void userRepositorySaveReturnsUser() {
//        when(userRepository.save(any())).thenReturn(user);
//    }
//
//    private void userRepositoryFindByNameReturnsUser() {
//        when(userRepository.findByName(any())).thenReturn(Optional.of(user));
//    }
//
//    private void userRepositoryFindByNameReturnsEmpty() {
//        when(userRepository.findByName(any())).thenReturn(Optional.empty());
//    }
//
//    private void tokenServiceCreateReturnsToken() {
//        when(tokenService.create(any())).thenReturn(token);
//    }

}
