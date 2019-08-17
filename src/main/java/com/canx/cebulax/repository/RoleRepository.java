package com.canx.cebulax.repository;

import com.canx.cebulax.model.Role;
import com.canx.cebulax.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    List<Role> findByRole(UserRole role);
}
