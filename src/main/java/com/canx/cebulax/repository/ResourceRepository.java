package com.canx.cebulax.repository;

import com.canx.cebulax.model.Group;
import com.canx.cebulax.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {

    Optional<Resource> findByName(String name);

    Optional<Resource> findByNameAndGroup(String name, Group group);
}
