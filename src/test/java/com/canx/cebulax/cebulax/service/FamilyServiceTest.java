package com.canx.cebulax.cebulax.service;

import com.canx.cebulax.cebulax.dto.UserCreateDTO;
import com.canx.cebulax.cebulax.exception.EntityAlreadyExistsException;
import com.canx.cebulax.cebulax.model.Family;
import com.canx.cebulax.cebulax.repository.FamilyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class FamilyServiceTest {

    private static final Family FAMILY = new Family("wrobel");

    @Mock
    private FamilyRepository familyRepository;
    private FamilyService familyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        familyService = new FamilyServiceImpl(familyRepository);
    }

    @Test
    void testCreateFamilyNonExistingName() {
        // given
        familyRepositorySaveReturnsFamily();
        familyRepositoryFindByNameReturnsEmpty();
        UserCreateDTO userCreateDTO = new UserCreateDTO(null, null, FAMILY.getName(), true);

        // when
        Family family = familyService.createFamily(userCreateDTO);

        // then
        assertThat(family.getName()).isEqualTo(FAMILY.getName());
    }

    @Test
    void testCreateFamilyExistingName() {
        // given
        familyRepositoryFindByNameReturnsFamily();
        UserCreateDTO userCreateDTO = new UserCreateDTO(null, null, FAMILY.getName(), true);

        // when + then
        assertThrows(EntityAlreadyExistsException.class, () -> familyService.createFamily(userCreateDTO));
    }

    private void familyRepositorySaveReturnsFamily() {
        when(familyRepository.save(any())).thenReturn(FAMILY);
    }

    private void familyRepositoryFindByNameReturnsFamily() {
        when(familyRepository.findByName(any())).thenReturn(Optional.of(FAMILY));
    }

    private void familyRepositoryFindByNameReturnsEmpty() {
        when(familyRepository.findByName(any())).thenReturn(Optional.empty());
    }
}
