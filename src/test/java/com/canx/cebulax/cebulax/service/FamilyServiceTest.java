package com.canx.cebulax.cebulax.service;

import com.canx.cebulax.cebulax.dto.FamilyCreateDTO;
import com.canx.cebulax.cebulax.exception.EntityAlreadyExistsException;
import com.canx.cebulax.cebulax.exception.EntityNotFoundException;
import com.canx.cebulax.cebulax.model.Family;
import com.canx.cebulax.cebulax.model.User;
import com.canx.cebulax.cebulax.repository.FamilyRepository;
import com.canx.cebulax.cebulax.repository.UserRepository;
import com.canx.cebulax.cebulax.security.PasswordEncoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class FamilyServiceTest {

    private User user;
    private Family family;

    private PasswordEncoder passwordEncoder;

    @Mock
    private FamilyRepository familyRepository;
    @Mock
    private UserRepository userRepository;
    private FamilyService familyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        passwordEncoder = new PasswordEncoder();
        familyService = new FamilyServiceImpl(familyRepository, userRepository, passwordEncoder);
        user = new User("roman", passwordEncoder.hashPassword("pass"));
        family = new Family("wrobel", passwordEncoder.hashPassword("passs"), user, new HashSet<User>() {{
            add(user);
        }});
    }

    @Test
    void testCreateFamilyNonExistingName() {
        // given
        familyRepositorySaveReturnsFamily();
        familyRepositoryFindByNameReturnsEmpty();
        userRepositoryFindByIdReturnsUser();

        FamilyCreateDTO familyCreateDTO = new FamilyCreateDTO("wrobel", "pass", 1L);

        // when
        Family family = familyService.createFamily(familyCreateDTO);

        // then
        assertThat(family.getName()).isEqualTo(this.family.getName());
    }

    @Test
    void testCreateFamilyWithExistingName() {
        // given
        familyRepositoryFindByNameReturnsFamily();
        userRepositoryFindByIdReturnsUser();

        FamilyCreateDTO familyCreateDTO = new FamilyCreateDTO("wrobel", "pass", 1L);

        // when + then
        assertThrows(EntityAlreadyExistsException.class, () -> familyService.createFamily(familyCreateDTO));
    }

    @Test
    void testCreateNonExistingUser() {
        // given
        familyRepositoryFindByNameReturnsEmpty();
        userRepositoryFindByIdReturnsEmpty();

        FamilyCreateDTO familyCreateDTO = new FamilyCreateDTO("wrobel", "pass", 1L);

        // when + then
        assertThrows(EntityNotFoundException.class, () -> familyService.createFamily(familyCreateDTO));
    }

    private void familyRepositorySaveReturnsFamily() {
        when(familyRepository.save(any())).thenReturn(family);
    }

    private void familyRepositoryFindByNameReturnsFamily() {
        when(familyRepository.findByName(any())).thenReturn(Optional.of(family));
    }

    private void familyRepositoryFindByNameReturnsEmpty() {
        when(familyRepository.findByName(any())).thenReturn(Optional.empty());
    }

    private void userRepositoryFindByIdReturnsUser() {
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
    }

    private void userRepositoryFindByIdReturnsEmpty() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());
    }
}
