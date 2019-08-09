package com.canx.cebulax.cebulax.service;

import com.canx.cebulax.cebulax.dto.FamilyDTO;
import com.canx.cebulax.cebulax.exception.FamilyAlreadyExistsException;
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
    public long createFamily(FamilyDTO family) {
        familyRepository.findByName(family.getName()).ifPresent(f -> {
            throw new FamilyAlreadyExistsException(family.getName());
        });

        return familyRepository.save(Family.fromDTO(family)).getId();
    }
}
