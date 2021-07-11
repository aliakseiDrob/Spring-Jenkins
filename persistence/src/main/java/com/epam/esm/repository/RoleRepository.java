package com.epam.esm.repository;

import com.epam.esm.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Role entity
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * finds role by name
     *
     * @param name role name
     * @return role
     */
    Role findByName(String name);
}