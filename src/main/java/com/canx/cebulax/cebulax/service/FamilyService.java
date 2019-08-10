package com.canx.cebulax.cebulax.service;

import com.canx.cebulax.cebulax.dto.FamilyCreateDTO;
import com.canx.cebulax.cebulax.model.Family;

public interface FamilyService {

    Family createFamily(FamilyCreateDTO familyCreateDTO);

    Family findByName(String familyName);
}
