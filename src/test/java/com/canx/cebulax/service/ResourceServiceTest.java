package com.canx.cebulax.service;

import com.canx.cebulax.dto.GroupCreateDTO;
import com.canx.cebulax.dto.ReservationCreateDTO;
import com.canx.cebulax.dto.ResourceCreateDTO;
import com.canx.cebulax.dto.UserCreateDTO;
import com.canx.cebulax.exception.*;
import com.canx.cebulax.model.Group;
import com.canx.cebulax.model.Reservation;
import com.canx.cebulax.model.Resource;
import com.canx.cebulax.model.User;
import com.canx.cebulax.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ResourceServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    GroupService groupService;

    @Autowired
    ResourceService sut;

    boolean initialized = false;
    String resourceName;
    User user1;
    User user2;
    User user3;
    Group group1;
    Group group2;

    @BeforeEach
    void setUpGroupServiceTest() {
        resourceName = UUID.randomUUID().toString().substring(0, 10);

        if (!initialized) {
            UserCreateDTO userInput1 = new UserCreateDTO(UUID.randomUUID().toString().substring(0, 10), "pass1");
            UserCreateDTO userInput2 = new UserCreateDTO(UUID.randomUUID().toString().substring(0, 10), "pass1");
            UserCreateDTO userInput3 = new UserCreateDTO(UUID.randomUUID().toString().substring(0, 10), "pass1");
            user1 = userService.createUser(userInput1);
            user2 = userService.createUser(userInput2);
            user3 = userService.createUser(userInput3);

            GroupCreateDTO groupInput1 = new GroupCreateDTO(UUID.randomUUID().toString().substring(0, 10), "pass1");
            GroupCreateDTO groupInput2 = new GroupCreateDTO(UUID.randomUUID().toString().substring(0, 10), "pass1");
            group1 = groupService.createGroup(groupInput1, user1.getId());
            group2 = groupService.createGroup(groupInput2, user2.getId());
            initialized = true;
        }
    }

    @Test
    void testCreateResourceUniqueNameExistingGroup() {
        // given
        ResourceCreateDTO resourceCreateDTO = new ResourceCreateDTO(resourceName, 1);

        // when
        Resource resource = sut.createResource(resourceCreateDTO, group1.getId());

        // then
        assertThat(resource.getId()).isNotNull();
        assertThat(resource.getName()).isEqualTo(resourceName);
        assertThat(resource.getGroup()).isEqualTo(group1);
    }

    @Test
    void testCreateResourceExistingNameExistingGroup() {
        // given
        ResourceCreateDTO resourceCreateDTO = new ResourceCreateDTO(resourceName, 1);
        sut.createResource(resourceCreateDTO, group1.getId());

        // when + then
        assertThrows(EntityAlreadyExistsException.class, () -> sut.createResource(resourceCreateDTO, group1.getId()));
    }

    @Test
    void testCreateResourceExistingNameDifferentGroup() {
        // given
        ResourceCreateDTO resourceCreateDTO = new ResourceCreateDTO(resourceName, 1);
        sut.createResource(resourceCreateDTO, group1.getId());

        // when
        Resource resource = sut.createResource(resourceCreateDTO, group2.getId());

        // then
        assertThat(resource.getId()).isNotNull();
        assertThat(resource.getName()).isEqualTo(resourceName);
        assertThat(resource.getGroup()).isEqualTo(group2);
    }

    @Test
    void testDeleteResourceAsGroupGroupOwner() {
        // given
        ResourceCreateDTO resourceCreateDTO = new ResourceCreateDTO(resourceName, 1);
        Resource resource = sut.createResource(resourceCreateDTO, group1.getId());

        // when
        sut.deleteResource(resource.getId(), user1.getId());

        // then
        assertThat(sut.findAll()).doesNotContain(resource);
    }

    @Test
    void testDeleteResourceAsNotGroupOwner() {
        // given
        ResourceCreateDTO resourceCreateDTO = new ResourceCreateDTO(resourceName, 1);
        Resource resource = sut.createResource(resourceCreateDTO, group1.getId());

        // when + then
        assertThrows(UnauthorizedException.class, () -> sut.deleteResource(resource.getId(), user2.getId()));
        assertThat(sut.findAll().stream().anyMatch(r->r.getId().equals(resource.getId()))).isTrue();
    }

    @Test
    void testAddReservation() {
        // given
        ResourceCreateDTO resourceCreateDTO = new ResourceCreateDTO(resourceName, 1);
        Resource resource = sut.createResource(resourceCreateDTO, group1.getId());
        LocalDateTime dateTo = LocalDateTime.now().plus(10, ChronoUnit.MINUTES);
        ReservationCreateDTO reservationCreateDTO = new ReservationCreateDTO(resource.getId(), dateTo);

        // when
        Reservation reservation = sut.addReservation(reservationCreateDTO, user1.getId());

        // then
        assertThat(reservation.getId()).isNotNull();
        assertThat(reservation.getResource().getId()).isEqualTo(resource.getId());
        assertThat(reservation.getUser()).isEqualTo(user1);
    }

    @Test
    void testAddReservationUserNotInGroup() {
        // given
        ResourceCreateDTO resourceCreateDTO = new ResourceCreateDTO(resourceName, 1);
        Resource resource = sut.createResource(resourceCreateDTO, group1.getId());
        LocalDateTime dateTo = LocalDateTime.now().plus(10, ChronoUnit.MINUTES);
        ReservationCreateDTO reservationCreateDTO = new ReservationCreateDTO(resource.getId(), dateTo);

        // when + then
        assertThrows(InvalidArgumentException.class, () -> sut.addReservation(reservationCreateDTO, user3.getId()));
    }

    @Test
    void testAddReservationUserWithExistingReservation() {
        // given
        ResourceCreateDTO resourceCreateDTO = new ResourceCreateDTO(resourceName, 1);
        Resource resource = sut.createResource(resourceCreateDTO, group1.getId());
        LocalDateTime dateTo = LocalDateTime.now().plus(10, ChronoUnit.MINUTES);
        ReservationCreateDTO reservationCreateDTO = new ReservationCreateDTO(resource.getId(), dateTo);
        sut.addReservation(reservationCreateDTO, user1.getId());

        // when + then
        assertThrows(EntityAlreadyExistsException.class, () -> sut.addReservation(reservationCreateDTO, user1.getId()));
    }

}
