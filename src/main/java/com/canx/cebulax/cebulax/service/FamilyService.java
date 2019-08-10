package com.canx.cebulax.cebulax.service;

import com.canx.cebulax.cebulax.dto.UserCreateDTO;
import com.canx.cebulax.cebulax.model.Family;

public interface FamilyService {

    Family createFamily(UserCreateDTO userCreateDTO);

    Family findByName(String familyName);
}
