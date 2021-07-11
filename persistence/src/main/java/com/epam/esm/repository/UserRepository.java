package com.epam.esm.repository;

import com.epam.esm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for User entity
 */

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * finds User by name
     *
     * @param name User name
     * @return Optional User can be null
     */
    Optional<User> findByName(String name);
}
