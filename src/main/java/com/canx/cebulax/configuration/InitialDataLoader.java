package com.canx.cebulax.configuration;

import com.canx.cebulax.model.Role;
import com.canx.cebulax.model.UserRole;
import com.canx.cebulax.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Order(1)
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(InitialDataLoader.class);

    private final RoleRepository roleRepository;
    private boolean alreadySetup = false;

    public InitialDataLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }
        createRolesIfNotFound(Arrays.asList(UserRole.values()));
        alreadySetup = true;
    }


    @Transactional
    protected void createRolesIfNotFound(List<UserRole> newRoles) {
        List<UserRole> existingRoles = roleRepository.findAll()
                .stream()
                .map(Role::getRole)
                .collect(Collectors.toList());

        List<Role> rolesToBePersisted = newRoles.stream()
                .filter(newRole -> !existingRoles.contains(newRole))
                .map(Role::new)
                .collect(Collectors.toList());

        roleRepository.saveAll(rolesToBePersisted);
        logger.info("Created roles: {}", rolesToBePersisted.stream().map(Role::getRole).toArray());
    }
}