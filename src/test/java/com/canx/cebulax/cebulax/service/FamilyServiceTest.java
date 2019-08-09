package com.canx.cebulax.cebulax.service;

import com.canx.cebulax.cebulax.dto.FamilyDTO;
import com.canx.cebulax.cebulax.exception.FamilyAlreadyExistsException;
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

    @Mock
    private FamilyRepository familyRepository;
    private FamilyService familyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        familyService = new FamilyServiceImpl(familyRepository);
    }

    @Test
    void testCreateFamilyValidFamily() {
        // given
        familyRepositorySaveReturnsFamily();
        familyRepositoryFindByNameReturnsEmpty();
        FamilyDTO familyDTO = new FamilyDTO("wrobel");

        // when
        long familyId = familyService.createFamily(familyDTO);

        // then
        assertThat(familyId).isEqualTo(1L);
    }

    @Test
    void testCreateUserExistingName() {
        // given
        familyRepositoryFindByNameReturnsFamily();
        FamilyDTO familyDTO = new FamilyDTO("wrobel");

        // when + then
        assertThrows(FamilyAlreadyExistsException.class, () -> familyService.createFamily(familyDTO));
    }

    private void familyRepositorySaveReturnsFamily() {
        when(familyRepository.save(any())).thenReturn(new Family(1L, "kowalski"));
    }

    private void familyRepositoryFindByNameReturnsFamily() {
        when(familyRepository.findByName(any())).thenReturn(Optional.of(new Family(1L, "kowalski")));
    }

    private void familyRepositoryFindByNameReturnsEmpty() {
        when(familyRepository.findByName(any())).thenReturn(Optional.empty());
    }
}
