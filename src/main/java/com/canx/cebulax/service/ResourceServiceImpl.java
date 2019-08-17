package com.canx.cebulax.service;

import com.canx.cebulax.dto.ResourceCreateDTO;
import com.canx.cebulax.exception.EntityAlreadyExistsException;
import com.canx.cebulax.exception.EntityNotFoundException;
import com.canx.cebulax.exception.InvalidActionException;
import com.canx.cebulax.model.Group;
import com.canx.cebulax.model.Resource;
import com.canx.cebulax.model.User;
import com.canx.cebulax.repository.GroupRepository;
import com.canx.cebulax.repository.ResourceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;
    private final GroupRepository groupRepository;

    public ResourceServiceImpl(ResourceRepository resourceRepository, GroupRepository groupRepository) {
        this.resourceRepository = resourceRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public Resource createResource(ResourceCreateDTO dto, Long groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() ->
                new EntityNotFoundException("Group with id", groupId.toString()));

        resourceRepository.findByNameAndGroup(dto.getName(), group).ifPresent(r -> {
            throw new EntityAlreadyExistsException("Resource with name", dto.getName());
        });

        Resource resource = new Resource(dto.getName(), dto.getSlots(), group);

        return resourceRepository.save(resource);
    }

    @Override
    public void deleteResource(Long resourceId, Long ownerId) {
        Resource resource = resourceRepository.findById(resourceId).orElseThrow(() ->
                new EntityNotFoundException("Resource with id", resourceId.toString()));

        User resourceGroupOwner = resource.getGroup().getOwner();

        if (resourceGroupOwner.getId() != ownerId) {
            throw new InvalidActionException("Delete resource", "can be done only by group owner");
        }

        resourceRepository.deleteById(resourceId);
    }

    @Override
    public List<Resource> findAll() {
        return resourceRepository.findAll();
    }
}
