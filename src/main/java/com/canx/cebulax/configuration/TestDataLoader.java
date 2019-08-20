package com.canx.cebulax.configuration;

import com.canx.cebulax.dto.GroupCreateDTO;
import com.canx.cebulax.dto.ReservationCreateDTO;
import com.canx.cebulax.dto.ResourceCreateDTO;
import com.canx.cebulax.dto.UserCreateDTO;
import com.canx.cebulax.model.Group;
import com.canx.cebulax.model.Resource;
import com.canx.cebulax.model.User;
import com.canx.cebulax.service.GroupService;
import com.canx.cebulax.service.ResourceService;
import com.canx.cebulax.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
@Profile("dev")
@Order(99)
public class TestDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(TestDataLoader.class);

    private final UserService userService;
    private final GroupService groupService;
    private final ResourceService resourceService;
    private boolean alreadySetup = false;

    public TestDataLoader(UserService userService, GroupService groupService, ResourceService resourceService) {
        this.userService = userService;
        this.groupService = groupService;
        this.resourceService = resourceService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }
        createTestData();
        logger.info("Test data created");
        alreadySetup = true;
    }


    @Transactional
    protected void createTestData() {
        User user1 = userService.createUser(new UserCreateDTO("user1", "pass"));
        User user2 = userService.createUser(new UserCreateDTO("user2", "pass"));
        User user3 = userService.createUser(new UserCreateDTO("user3", "pass"));

        Group group1 = groupService.createGroup(new GroupCreateDTO("group1", "pass"), user1.getId());
        Group group2 = groupService.createGroup(new GroupCreateDTO("group2", "pass"), user2.getId());
        Group group3 = groupService.createGroup(new GroupCreateDTO("group3", "pass"), user3.getId());

        groupService.joinGroup(group1.getId(), "pass", user2.getId());
        groupService.joinGroup(group1.getId(), "pass", user3.getId());
        groupService.joinGroup(group2.getId(), "pass", user1.getId());

        Resource res1 = resourceService.createResource(new ResourceCreateDTO("res1", 3), group1.getId());
        Resource res2 = resourceService.createResource(new ResourceCreateDTO("res2", 1), group1.getId());
        Resource res3 = resourceService.createResource(new ResourceCreateDTO("res3", 5), group1.getId());
        Resource res4 = resourceService.createResource(new ResourceCreateDTO("res4", 2), group1.getId());

        resourceService.addReservation(new ReservationCreateDTO(res1.getId(), LocalDateTime.now().plus(30, ChronoUnit.SECONDS)), user1.getId());
        resourceService.addReservation(new ReservationCreateDTO(res1.getId(), LocalDateTime.now().plus(15, ChronoUnit.MINUTES)), user2.getId());
        resourceService.addReservation(new ReservationCreateDTO(res1.getId(), LocalDateTime.now().plus(60, ChronoUnit.MINUTES)), user3.getId());
        resourceService.addReservation(new ReservationCreateDTO(res2.getId(), LocalDateTime.now().plus(60, ChronoUnit.MINUTES)), user1.getId());

    }
}