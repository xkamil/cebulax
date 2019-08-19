package com.canx.cebulax.service;

import com.canx.cebulax.dto.ReservationCreateDTO;
import com.canx.cebulax.dto.ResourceCreateDTO;
import com.canx.cebulax.exception.EntityAlreadyExistsException;
import com.canx.cebulax.exception.EntityNotFoundException;
import com.canx.cebulax.exception.InvalidActionException;
import com.canx.cebulax.exception.InvalidArgumentException;
import com.canx.cebulax.model.Group;
import com.canx.cebulax.model.Reservation;
import com.canx.cebulax.model.Resource;
import com.canx.cebulax.model.User;
import com.canx.cebulax.repository.GroupRepository;
import com.canx.cebulax.repository.ReservationRepository;
import com.canx.cebulax.repository.ResourceRepository;
import com.canx.cebulax.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;
    private final GroupRepository groupRepository;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    public ResourceServiceImpl(ResourceRepository resourceRepository, GroupRepository groupRepository,
                               ReservationRepository reservationRepository, UserRepository userRepository) {
        this.resourceRepository = resourceRepository;
        this.groupRepository = groupRepository;
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
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
    @Transactional
    public void deleteResource(Long resourceId, Long ownerId) {
        Resource resource = resourceRepository.findById(resourceId).orElseThrow(() ->
                new EntityNotFoundException("Resource with id", resourceId.toString()));

        User resourceGroupOwner = resource.getGroup().getOwner();

        if (!resourceGroupOwner.getId().equals(ownerId)) {
            throw new InvalidActionException("Delete resource", "can be done only by group owner");
        }

        resourceRepository.deleteById(resourceId);
    }

    @Override
    public List<Resource> findAll() {
        return resourceRepository.findAll();
    }

    @Override
    @Transactional
    public Reservation addReservation(ReservationCreateDTO dto, Long userId) {
        LocalDateTime currentDateTime = LocalDateTime.now();

        Resource resource = resourceRepository.findById(dto.getResourceId()).orElseThrow(() ->
                new EntityNotFoundException("Resource with id", dto.getResourceId().toString()));

        User user = userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException("User with id", userId.toString()));

        if (dto.getDateTo().compareTo(currentDateTime) <= 0) {
            throw new InvalidArgumentException("dateTo", "should be future date");
        }

        if (!resource.getGroup().getUsers().contains(user)) {
            throw new InvalidArgumentException("userId", "is not a member of resource group");
        }

        List<Reservation> activeReservations = resource.getReservations()
                .stream()
                .filter(reservation -> reservation.getDateTo().compareTo(currentDateTime) > 0)
                .collect(Collectors.toList());

        if (activeReservations.stream().anyMatch(reservation -> reservation.getUser().equals(user))) {
            throw new InvalidArgumentException("user", "already has active reservation fot this resource");
        }

        if (activeReservations.size() < resource.getSlots()) {
            Reservation newReservation = new Reservation(dto.getDateTo(), resource, user);
            return reservationRepository.save(newReservation);
        } else {
            throw new InvalidActionException("Add reservation", "all resource slots are taken");
        }
    }
}
