package com.canx.cebulax.service;

import com.canx.cebulax.dto.UserAuthenticateDTO;
import com.canx.cebulax.dto.UserCreateDTO;
import com.canx.cebulax.exception.EntityAlreadyExistsException;
import com.canx.cebulax.exception.EntityNotFoundException;
import com.canx.cebulax.model.JwtAuthenticationResponse;
import com.canx.cebulax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService sut;

    private String userName;

    @BeforeEach
    void setUpUserServiceTest(){
        userName = UUID.randomUUID().toString().substring(0,10);
    }

    @Test
    void testCreateUserNonExistingName() {
        // given
        UserCreateDTO userCreateInput = new UserCreateDTO(userName, "pass1");

        // when
        User createdUser = sut.createUser(userCreateInput);

        // then
        assertThat(createdUser.getId()).isNotNull();
        assertThat(createdUser.getName()).isEqualTo(userName);
        assertThat(createdUser.getRoles()).contains("USER");
    }

    @Test
    void testCreateUserExistingName() {
        // given
        UserCreateDTO userCreateInput1 = new UserCreateDTO(userName, "pass1");
        UserCreateDTO userCreateInput2 = new UserCreateDTO(userName, "pass1");
        sut.createUser(userCreateInput1);

        // when + then
        assertThrows(EntityAlreadyExistsException.class, () -> sut.createUser(userCreateInput2));
    }

    @Test
    void testFindByIdExistingId() {
        // given
        UserCreateDTO userCreateInput = new UserCreateDTO(userName, "pass1");
        User createdUser = sut.createUser(userCreateInput);

        // when
        User user = sut.findById(createdUser.getId());


        // then
        assertThat(user).isEqualTo(createdUser);
    }

    @Test
    void testFindByIdNonExistingId() {
        // given
        Long nonExistingUserId = Long.MAX_VALUE;

        // when + then
        assertThrows(EntityNotFoundException.class, () -> sut.findById(nonExistingUserId));
    }

    @Test
    void testAuthenticateValidCredentials() {
        // given
        UserCreateDTO userCreateInput = new UserCreateDTO(userName, "pass1");
        sut.createUser(userCreateInput);
        UserAuthenticateDTO userAuthenticateDTO = new UserAuthenticateDTO(userName, "pass1");

        // when
        JwtAuthenticationResponse jwt = sut.authenticate(userAuthenticateDTO);

        // then
        assertThat(jwt.getAccessToken()).isNotEmpty();
        assertThat(jwt.getTokenType()).isEqualTo("Bearer");
    }

    @Test
    void testAuthenticateInvalidCredentials() {
        // given
        UserCreateDTO userCreateInput = new UserCreateDTO(userName, "pass1");
        sut.createUser(userCreateInput);
        UserAuthenticateDTO userAuthenticateDTO = new UserAuthenticateDTO(userName, "pass12");

        // when + then
        assertThrows(BadCredentialsException.class, () -> sut.authenticate(userAuthenticateDTO));
    }

}
