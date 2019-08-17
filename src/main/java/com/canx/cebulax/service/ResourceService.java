package com.canx.cebulax.service;

import com.canx.cebulax.dto.ResourceCreateDTO;
import com.canx.cebulax.model.Resource;

import java.util.List;

public interface ResourceService {

    Resource createResource(ResourceCreateDTO resourceCreateDto, Long groupId);

    void deleteResource(Long resourceId, Long ownerId);

    List<Resource> findAll();
}
