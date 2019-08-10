package com.canx.cebulax.cebulax.service;

import com.canx.cebulax.cebulax.dto.UserCreateDTO;
import com.canx.cebulax.cebulax.exception.EntityAlreadyExistsException;
import com.canx.cebulax.cebulax.exception.EntityNotFoundException;
import com.canx.cebulax.cebulax.model.Family;
import com.canx.cebulax.cebulax.repository.FamilyRepository;
import org.springframework.stereotype.Service;

@Service
public class FamilyServiceImpl implements FamilyService {

    private final FamilyRepository familyRepository;

    public FamilyServiceImpl(FamilyRepository familyRepository) {
        this.familyRepository = familyRepository;
    }

    @Override
    public Family createFamily(UserCreateDTO userCreateDTO) {
        familyRepository.findByName(userCreateDTO.getFamilyName()).ifPresent(f -> {
            throw new EntityAlreadyExistsException("Family", userCreateDTO.getName());
        });

        return familyRepository.save(Family.fromDTO(userCreateDTO));
    }

    @Override
    public Family findByName(String familyName) {
        return familyRepository.findByName(familyName).orElseThrow(() ->
                new EntityNotFoundException("Family ", familyName));
    }
}
