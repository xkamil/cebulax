package com.canx.cebulax.service;

import com.canx.cebulax.dto.ReservationCreateDTO;
import com.canx.cebulax.dto.ResourceCreateDTO;
import com.canx.cebulax.exception.EntityAlreadyExistsException;
import com.canx.cebulax.exception.EntityNotFoundException;
import com.canx.cebulax.exception.InvalidArgumentException;
import com.canx.cebulax.exception.UnauthorizedException;
import com.canx.cebulax.model.Group;
import com.canx.cebulax.model.Reservation;
import com.canx.cebulax.model.Resource;
import com.canx.cebulax.model.User;
import com.canx.cebulax.repository.ReservationRepository;
import com.canx.cebulax.repository.ResourceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;
    private final GroupService groupService;
    private final UserService userService;
    private final ReservationRepository reservationRepository;


    public ResourceServiceImpl(ResourceRepository resourceRepository, GroupService groupService,
                               ReservationRepository reservationRepository, UserService userService) {
        this.resourceRepository = resourceRepository;
        this.groupService = groupService;
        this.reservationRepository = reservationRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public Resource createResource(ResourceCreateDTO dto, Long groupId) {
        Group group = groupService.findById(groupId);
        resourceRepository.findByNameAndGroup(dto.getName(), group).ifPresent(r -> {
            throw new EntityAlreadyExistsException("Resource with name " + dto.getName());
        });
        Resource resource = new Resource(dto.getName(), dto.getSlots(), group);
        return resourceRepository.save(resource);
    }

    @Override
    public Resource findById(Long id) {
        return resourceRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Resource with id " + id.toString()));
    }

    @Override
    @Transactional
    public void deleteResource(Long resourceId, Long ownerId) {
        Resource resource = findById(resourceId);
        User resourceGroupOwner = resource.getGroup().getOwner();
        if (!resourceGroupOwner.getId().equals(ownerId)) {
            throw new UnauthorizedException("Only group owner can delete resource from group");
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
        Resource resource = findById(dto.getResourceId());
        User user = userService.findById(userId);

        if (!resource.getGroup().getUsers().contains(user)) {
            throw new InvalidArgumentException("userId is not a member of resource group");
        }

        List<Reservation> activeReservations = resource.getReservations()
                .stream()
                .filter(reservation -> reservation.getDateTo().compareTo(currentDateTime) > 0)
                .collect(Collectors.toList());

        if (activeReservations.stream().anyMatch(reservation -> reservation.getUser().equals(user))) {
            throw new EntityAlreadyExistsException("Active user reservation for resource");
        }

        if (activeReservations.size() < resource.getSlots()) {
            Reservation newReservation = new Reservation(dto.getDateTo(), resource, user);
            return reservationRepository.save(newReservation);
        } else {
            throw new EntityAlreadyExistsException("All reservations for this resource ");
        }
    }
}
